import React, {FormEvent} from "react";
import {Button, Card, Form} from "react-bootstrap";
import {Configuration, StudentApi, UserApi} from "./api";
import {delay} from "./Util";
import {toast} from "react-hot-toast";
import {NavigateFunction} from "react-router/lib/hooks";
import {useNavigate} from "react-router-dom";

interface Props {
    config: Configuration
    navigate: NavigateFunction
}

interface State {
    name: string
}

class RegisterStudent extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            name: ''
        }
    }

    async componentDidMount() {
        // HACK: redirect if user is logged in and student
        const userApi = new UserApi(this.props.config);
        const me = await userApi.me().then(res => res.data);

        if (me?.student) {
            this.props.navigate('/')
        }
    }

    async register(event: FormEvent<HTMLFormElement>) {
        event.preventDefault()
        const studentApi = new StudentApi(this.props.config)

        await toast.promise(
            studentApi.createStudent({
                name: this.state.name
            }), {
                loading: 'Registrierung...',
                success: 'Registrierung erfolgreich',
                error: 'Registrierung fehlgeschlagen',
            })
            .then(async _ => await delay(500))
            .then(_ => window.location.reload());
    }

    render() {
        return <Form  onSubmit={async event => await this.register(event)}>
            <Card className="register-card">
                <Card.Header>
                    Registrierung
                </Card.Header>
                <Card.Body>
                    <Card.Text>
                        <p>Wenn Sie sich als SchülerIn / StudentIn registrieren können Sie:</p>
                        <ul>
                            <li>sich bei einem Bildungsangebot anmelden</li>
                            <li>zugehörige Prüfungen einsehen</li>
                            <li>Ihre Benotung einsehen.</li>
                        </ul>
                        <Form.Control
                            placeholder="Name"
                            value={this.state.name}
                            onChange={e => this.setState({name: e.target.value})}/>
                    </Card.Text>
                </Card.Body>
                <Card.Footer>
                    <Button disabled={this.state.name.trim() === ''} type="submit">Registieren</Button>
                </Card.Footer>
            </Card>
        </Form>;
    }
}

export default function RegistrerStudentWithNavigation(props: {config: Configuration}) {
    const navigate = useNavigate()

    return <RegisterStudent config={props.config} navigate={navigate}/>
}