import React from "react";
import {Configuration, Education, EducationApi, Institution, InstitutionApi, Location, LocationApi} from "./api";
import {Button, Card} from "react-bootstrap";
import {useParams} from "react-router-dom";

interface Props {
    config: Configuration
}

interface PropsWithParams extends Props {
    id: number
}

interface State {
    institution: Institution | null
    locations: Array<Location>
}

class InstitutionDetails extends React.Component<PropsWithParams, State> {
    constructor(props: PropsWithParams) {
        super(props);
        this.state = {
            institution: null,
            locations: []
        }
    }

    async componentDidMount() {
        const institutionApi = new InstitutionApi(this.props.config)
        const locationApi = new LocationApi(this.props.config)

        const institution = await institutionApi
            .singleInstitution(this.props.id)
            .then((i) => i.data)

        const locations = await locationApi
            .allLocationsOfInstitution(institution.id!)
            .then((l) => l.data)

        this.setState({
            institution: institution,
            locations: locations
        })
    }

    render() {
        const locations = this.state.locations.map((l) => <li>{l.address}, {l.zip} {l.city}</li>)

        return <Card className="institution-card">
            <Card.Body>
                <Card.Title>{this.state.institution?.name}</Card.Title>
                <Card.Text>
                    Standorte:
                    <ul>{locations}</ul>
                </Card.Text>
            </Card.Body>
        </Card>
    }
}

export default (props: Props) => {
    let {id} = useParams();
    return <InstitutionDetails {...props} id={parseInt(id!)}/>
}