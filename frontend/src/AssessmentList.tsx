import React from "react";
import {
    Assessment,
    Education,
    Grade, Graduation
} from "./api";

import {Card, Table} from "react-bootstrap";
import {CheckmarkIcon} from "react-hot-toast";

interface Element {
    education: Education,
    assessments: Array<Assessment>
    grades: Map<number, Grade>
    graduation: Graduation | null
}

interface Props {
    element: Element
}

interface State {
    element: Element
}


export default class AssessmentList extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {element: props.element}
    }

    render() {
        return <Card className="assessment-card">
            <Card.Header>
                <a href={`${process.env.PUBLIC_URL}/education/${this.state.element.education.id}`}>{this.state.element.education.title}</a>
                { this.state.element.graduation && <CheckmarkIcon className="checkmark"/> }
            </Card.Header>
            <Card.Body>
                <Table >
                    <thead>
                        <tr>
                            <th>Prüfung</th>
                            <th>Benotung</th>
                            <th>Datum der Veröffentlichung</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.element.assessments.map((a) => {
                            const grade = this.state.element.grades.get(a.id!)
                            return <tr>
                                <td>{a.title}</td>
                                <td>{grade?.grade ?? ""}</td>
                                <td>{grade?.date ?? ""}</td>
                            </tr>
                        })}
                    </tbody>
                </Table>
            </Card.Body>
        </Card>;
    }
}