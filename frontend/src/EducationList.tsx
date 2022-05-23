import React from "react";
import Card from "react-bootstrap/Card";
import {Education, Institution} from "./api";

interface Element {
    institution: Institution
    education: Education
}

interface Props {
    elements: Array<Element>
}

export default function EducationList(props: Props) {
    const formattedElements = props.elements.map((e) =>
        <Card className="education-card">
            <Card.Header>
                <a href={`${process.env.PUBLIC_URL}/institution/${e.institution.id}`}>{e.institution.name}</a>
            </Card.Header>
            <Card.Body>
                <Card.Title>
                    <a href={`${process.env.PUBLIC_URL}/education/${e.education.id}`}>{e.education.title}</a>
                </Card.Title>
                <Card.Text>{e.education.description}</Card.Text>
            </Card.Body>
        </Card>
    )

    return <div>
        {formattedElements}
    </div>
}