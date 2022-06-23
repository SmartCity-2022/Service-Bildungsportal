import React from 'react';

import EducationList from "./EducationList";

import {Configuration, Education, EducationApi, Institution, InstitutionApi, Location, LocationApi} from "./api";
import {Form} from "react-bootstrap";

interface Element {
    institution: Institution
    location: Location
    education: Education
}

interface Props {
    config: Configuration
}

interface State {
    elements: Array<Element>
    institutions: Map<string, Institution>
    selectedInstitution: Institution | null
    title: string
}

export default class Overview extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            elements: [],
            institutions: new Map(),
            selectedInstitution: null,
            title: ''
        }
    }

    async componentDidMount() {
        const institutionApi = new InstitutionApi(this.props.config)
        const locationApi = new LocationApi(this.props.config)
        const educationApi = new EducationApi(this.props.config)

        const institutions = await institutionApi
            .allInstitutions()
            .then((response) => response.data)

        const elements = await Promise.all(institutions)
            .then(async (institutions) => {
                let locations = await Promise.all(institutions.map(async (i) => {
                    return {
                        intitution: i,
                        locations: await locationApi.allLocationsOfInstitution(i.id!)
                    }
                }))

                return locations.flatMap((location) => {
                    return location.locations.data.map((l) => {
                        return {
                            institution: location.intitution,
                            location: l
                        }
                    })
                })
            })
            .then(async (locations) => {
                let educations = await Promise.all(locations.map(async (l) => {
                    return {
                        institution: l.institution,
                        location: l.location,
                        educations: await educationApi.allEducationsOfLocation(l.location.id!)
                    }
                }))

                return educations.flatMap((education) => {
                    return education.educations.data.map((e) => {
                        return {
                            institution: education.institution,
                            location: education.location,
                            education: e,
                        }
                    })
                })
            })

        this.setState({
            institutions: new Map(institutions.map((i) => [i.id?.toString() ?? "", i])),
            elements: elements
        })
    }

    render() {
        return <div>
            <div className="filter">
                <Form.Select
                    onChange={ e =>
                        this.setState({selectedInstitution: this.state.institutions.get(e.target.value) ?? null})
                    }>
                    <option value="" selected>Institution</option>
                    {
                        Array.from(this.state.institutions.values())
                            .map((i) => <option value={i.id}>{i.name}</option>)
                    }
                </Form.Select>
                <Form.Control
                    value={this.state.title}
                    onChange={ e => {
                        this.setState({title: e.target.value})
                    }}
                    placeholder="Titel"/>
            </div>
            <EducationList elements={this.state.elements.filter(e => {
                return (!this.state.selectedInstitution || e.institution.id === this.state.selectedInstitution?.id) &&
                    e.education.title.includes(this.state.title)
            })}/>
        </div>;
    }

}
