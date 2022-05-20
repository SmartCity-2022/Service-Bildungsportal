
import 'bootstrap/dist/css/bootstrap.min.css'
import 'material-colors/dist/colors.var.css'

import './common.css';
import './index.css';

import React from 'react';
import ReactDOM from 'react-dom/client';
import reportWebVitals from './reportWebVitals';
import {BrowserRouter, Route, Routes} from "react-router-dom";

import Header from "./Header";
import Overview from './Overview';
import EducationDetails from "./EducationDetails";
import InstitutionDetails from "./InstitutionDetails";

import {Configuration} from "./api";

const config = new Configuration({
    basePath: process.env.REACT_APP_BACKEND
})

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);
root.render(
    <React.StrictMode>
        <Header/>
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Overview config={config}/>}/>
                <Route path="/education/:id" element={<EducationDetails config={config}/>}/>
                <Route path="/institution/:id" element={<InstitutionDetails config={config}/>}/>
            </Routes>
        </BrowserRouter>
    </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
