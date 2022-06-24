import React, {FormEvent} from 'react';

import {Configuration, Education, EducationApi, Institution, Location, LocationApi, UserApi} from "./api";
import {Button, Card, Form} from "react-bootstrap";
import {toast} from "react-hot-toast";
import {useNavigate} from "react-router-dom";
import {NavigateFunction} from "react-router/lib/hooks";
import {delay} from "./Util";

interface Props {
    config: Configuration
    navigate: NavigateFunction
}

interface State {
    institution: Institution | null
    locations: Map<string, Location>
    location: Location | null
    title: string,
    description: string
}

class EducationCreation extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            institution: null,
            locations: new Map(),
            location: null,
            title: '',
            description: ''
        }
    }

    async componentDidMount() {
        const userApi = new UserApi(this.props.config)
        const locationApi = new LocationApi(this.props.config)

        const me = await userApi.me().then(res => res.data)
        const locations = await locationApi.allLocationsOfInstitution(me.institution?.id!!)
            .then(res => res.data)

        this.setState({
            institution: me.institution!!,
            locations: new Map(locations.map((l) => [l.id!!.toString(), l])),
            location: locations[0]
        })
    }

    async handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault()
        const education = {
            title: this.state.title,
            description: this.state.description
        }

        const educationApi = new EducationApi(this.props.config)
        await toast.promise(
            educationApi.createEducationOfLocation(this.state.location?.id!, education),
            {
                loading: 'Bildungsangebot wird erstellt',
                success: 'Bildungsangebot erfolgreich erstellt',
                error: 'Bildungsangebot konnte nicht erstellt werden'
            })
            .then(_ => delay(500))
            .then(_ => this.props.navigate('/'))
    }

    locationString(location: Location | null): string {
        return `${this.state.institution?.name} - ${location?.address}, ${location?.zip} ${location?.city}`
    }

    render() {
        return <Form onSubmit={async (e) => await this.handleSubmit(e)}>
            <Card className="education-card">
                <Card.Header>
                    <Form.Select
                        value={this.state.location?.id!.toString()}
                        onChange={(e) => this.setState({location: this.state.locations.get(e.target.value)!!})}>
                        {Array.from(this.state.locations.entries(), ([id, l]) => <option value={id}>{this.locationString(l)}</option>)}
                    </Form.Select>
                </Card.Header>
                <Card.Body>
                    <Card.Title>
                        <Form.Control
                            placeholder="Titel"
                            value={this.state.title}
                            onChange={(e) => this.setState({title: e.target.value})}/>
                    </Card.Title>
                    <Card.Text>
                        <Form.Control
                            as="textarea"
                            placeholder="Beschreibung"
                            value={this.state.description}
                            onChange={(e) => this.setState({description: e.target.value})}/>
                    </Card.Text>
                </Card.Body>
                <Card.Footer>
                    <Button type={"submit"}>Erstellen</Button>
                </Card.Footer>
            </Card>
        </Form>;
    }
}

export default function EducationCreationWithNavigation(props: {config: Configuration}) {
    const navigate = useNavigate()

    return <EducationCreation config={props.config} navigate={navigate}/>
}