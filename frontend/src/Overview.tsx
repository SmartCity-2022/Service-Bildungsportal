import React from 'react';

import EducationList from "./EducationList";

import {Configuration, Education, EducationApi, Institution, InstitutionApi, Location, LocationApi} from "./api";

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
}

export default class Overview extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {elements: []}
    }

    async componentDidMount() {
        const institutionApi = new InstitutionApi(this.props.config)
        const locationApi = new LocationApi(this.props.config)
        const educationApi = new EducationApi(this.props.config)

        const elements = await institutionApi
            .allInstitutions()
            .then((response) => response.data)
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
            elements: elements
        })
    }

    render() {
        return <div>
            <EducationList elements={this.state.elements}/>
        </div>;
    }

}
