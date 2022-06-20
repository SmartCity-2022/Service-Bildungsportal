import React from "react";
import {
    Configuration,
    Education,
    EducationApi,
    Institution,
    InstitutionApi,
    LocationApi, Matriculation, MatriculationApi, Qualification,
    QualificationApi, User, UserApi
} from "./api";
import {Button, Card} from "react-bootstrap";
import {useParams} from "react-router-dom";
import {toast} from "react-hot-toast";
import Resources from "./Resources";

interface Props {
    config: Configuration
}

interface PropsWithParams extends Props {
    id: number
}

interface State {
    institution: Institution | null
    education: Education | null
    qualifications: Array<Qualification>,
    me: User | null,
    matriculations: Array<Matriculation>
}

class EducationDetails extends React.Component<PropsWithParams, State> {
    matriculationApi: MatriculationApi

    constructor(props: PropsWithParams) {
        super(props);
        this.state = {
            institution: null,
            education: null,
            qualifications: [],
            me: null,
            matriculations: []
        }
        this.matriculationApi = new MatriculationApi(this.props.config)
    }

    async componentDidMount() {
        const educationApi = new EducationApi(this.props.config)
        const locationApi = new LocationApi(this.props.config)
        const institutionApi = new InstitutionApi(this.props.config)
        const qualificationApi = new QualificationApi(this.props.config)
        const userApi = new UserApi(this.props.config)

        const education = await educationApi
            .singleEducation(this.props.id)
            .then((response) => response.data)

        const qualifications = await qualificationApi
            .allQualificationsOfEducation(this.props.id)
            .then((response) => response.data)
            .then((qualifications) => {
                return Promise.all(qualifications.map((q) => {
                    return qualificationApi
                        .singleQualification(q)
                        .then((response) => response.data)
                }))
            })

        const location = await locationApi
            .singleLocation(education.locationId!)
            .then((response) => response.data)

        const institution = await institutionApi
            .singleInstitution(location.institutionId!)
            .then((response) => response.data)

        const me = await userApi
            .me()
            .then((response) => response.data)
            .catch((reason) => null)

        const matriculations = await this.matriculationApi
            .myMatriculations()
            .then((res) => res.data)

        this.setState({
            institution: institution,
            education: education,
            qualifications: qualifications,
            me: me,
            matriculations: matriculations
        })
    }

    getQualifications() {
        if (this.state.qualifications.length > 0) {
            const qualifications = this.state.qualifications.map((q) => <li>{q.name}</li>)
            return <p>
                Voraussetzungen:
                <ul>{qualifications}</ul>
            </p>
        } else {
            return;
        }
    }

    async matriculate() {
        if (this.state.education?.id != null && this.state.me?.student?.id != null) {
            const promise = this.matriculationApi
                .createMatriculationOfEducation(this.state.education.id, this.state.me.student.id)
                .then((res) => this.matriculationApi.myMatriculations())
                .then((res) => res.data)

            const matriculations = await toast.promise(
                promise, {
                    loading: 'Einschreiben...',
                    success: 'Erfolgreich eingeschrieben',
                    error: 'Fehler beim Einschreiben'
                })

            this.setState({
                matriculations: matriculations
            })
        } else {
            toast.error('Einschreibung nicht möglich')
        }
    }

    canMatriculate() {
        return this.state.me !== null
            && this.state.matriculations.every((m) => m.educationId !== this.props.id)
    }

    render() {
        return <Card className="education-card">
            <Card.Header>
                <a href={`${process.env.PUBLIC_URL}/${Resources.institutionDetails(this.state.institution?.id)}`}>{this.state.institution?.name}</a>
            </Card.Header>
            <Card.Body>
                <Card.Title>{this.state.education?.title}</Card.Title>
                <Card.Text>
                    <p>{this.state.education?.description}</p>
                    {this.getQualifications()}
                    <Button disabled={!this.canMatriculate()} onClick={async event => await this.matriculate()}>Einschreiben</Button>
                </Card.Text>
            </Card.Body>
        </Card>
    }
}

export default (props: Props) => {
    let {id} = useParams();
    return <EducationDetails {...props} id={parseInt(id!)}/>
}