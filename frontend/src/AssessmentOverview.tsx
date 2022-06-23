import React from "react";
import {
    Assessment,
    AssessmentApi,
    Configuration,
    Education,
    EducationApi,
    Grade,
    GradeApi, Graduation, GraduationApi,
    MatriculationApi
} from "./api";

import AssessmentList from "./AssessmentList";

interface Element {
    education: Education,
    assessments: Array<Assessment>
    grades: Map<number, Grade>,
    graduation: Graduation | null,
}

interface Props {
    config: Configuration
}

interface State {
    elements: Array<Element>
}


export default class AssessmentOverview extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {elements: []}
    }

    async componentDidMount() {
        const educationApi = new EducationApi(this.props.config)
        const matriculationApi = new MatriculationApi(this.props.config)
        const gradeApi = new GradeApi(this.props.config)
        const graduationApi = new GraduationApi(this.props.config)
        const assessmentApi = new AssessmentApi(this.props.config)

        const elements = await matriculationApi.myMatriculations()
            .then(async (res) => await Promise.all(
                res.data.map(async (m) => {
                    return {
                        education: await educationApi.singleEducation(m.educationId!)
                            .then((res) => res.data),
                        assessments: await assessmentApi.allAssessmentsOfEducation(m.educationId!)
                            .then((res) => res.data),
                        grades: await gradeApi.allGradesOfMatriculation(m.id!)
                            .then((res) => new Map(res.data.map((g) => [g.assessmentId!, g]))),
                        graduation: await graduationApi.allGraduationsOfMatriculation(m.id!).then(res => res.data[0])
                    }
                })
            ))

        this.setState({
            elements: elements
        })
    }

    render() {
        return <div>
            {this.state.elements.map((e) => <AssessmentList element={e}/>)}
        </div>;
    }
}