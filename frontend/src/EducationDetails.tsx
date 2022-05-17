import React from "react";
import {
    Configuration,
    Education,
    EducationApi,
    Institution,
    InstitutionApi,
    LocationApi, Qualification,
    QualificationApi
} from "./api";
import {Card} from "react-bootstrap";
import {useParams} from "react-router-dom";

interface Props {
    config: Configuration
}

interface PropsWithParams extends Props {
    id: number
}

interface State {
    institution: Institution | null
    education: Education | null
    qualifications: Array<Qualification>
}

class EducationDetails extends React.Component<PropsWithParams, State> {
    constructor(props: PropsWithParams) {
        super(props);
        this.state = {
            institution: null,
            education: null,
            qualifications: []
        }
    }

    async componentDidMount() {
        const educationApi = new EducationApi(this.props.config)
        const locationApi = new LocationApi(this.props.config)
        const institutionApi = new InstitutionApi(this.props.config)
        const qualificationApi = new QualificationApi(this.props.config)

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


        this.setState({
            institution: institution,
            education: education,
            qualifications: qualifications
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

    render() {
        return <Card className="education-card">
            <Card.Header>
                <a href={`/institution/${this.state.institution?.id}`}>{this.state.institution?.name}</a>
            </Card.Header>
            <Card.Body>
                <Card.Title>{this.state.education?.title}</Card.Title>
                <Card.Text>
                    <p>{this.state.education?.description}</p>
                    {this.getQualifications()}
                </Card.Text>
            </Card.Body>
        </Card>
    }
}

export default (props: Props) => {
    let {id} = useParams();
    return <EducationDetails {...props} id={parseInt(id!)}/>
}