import React, { useContext, useState } from "react";
import { useHistory } from "react-router-dom";
import axios from "axios";
import swal from "sweetalert";
import HomeHeader from "../home/HomeHeader";
import { useForm } from "react-hook-form";

import {
  FormControl,
  TextField,
  InputLabel,
  TextareaAutosize,
  Button,
  Typography,
  ButtonGroup,
} from "@material-ui/core";
import Container from "@material-ui/core/Container";

import { AuthContext } from "../app-context/AuthContext";

function SMSTemplate() {
  const usehistory = useHistory();

  //let userAction;
  let submitHandler;

  const [templateName, setTemplateName] = useState();
  const [templateText, setTemplateText] = useState();

  const { isLoggedIn } = useContext(AuthContext);

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  console.log("in SMS Template before move to home");
  console.log(isLoggedIn);

  if (!isLoggedIn) {
    usehistory.push("/");
  }

  let nameInput, textInput, message;

  const saveHandler = (event) => {
    // event.preventDefault();
    console.log("SMS Save submitHandler");

    const payload = JSON.stringify({
      tname: event.templateName,
      text: event.templateText,
    });
    axios
      .post(`api/template/create`, {
        tname: event.templateName,
        text: event.templateText,
      })
      .then((res) => {
        console.log(res.data);
      });

    swal({
      title: "Success",
      text: "SMS template created successfully!",
      icon: "success",
    });

    usehistory.push("/loginform");
  };

  const cancelHandler = (event) => {
    event.preventDefault();
    console.log("SMS Cancel submitHandler");
    usehistory.push("/loginform");
  };

  return (
    <React.Fragment>
      {/* <HomeHeader/> */}
      <br />
      <Container maxWidth="md">
        <FormControl onSubmit={submitHandler} size="medium">
          <Typography variant="h6">SMS Template</Typography>
          <br />

          {/* <TextField label= "Template Name" variant="outlined" type="Text" value={templateName} onChange={ event => {setTemplateName(event.target.value)}}></TextField> */}
          <TextField
            label="Template Name"
            variant="outlined"
            type="Text"
            {...register("templateName", {
              required: "Input required",
              maxLength: { value: 50, message: "Maximum 50 characters" },
            })}
          />
          {errors.templateName && errors.templateName.message}
          <br />

          {/* <TextareaAutosize placeholder= "Template Text" value={templateText} onChange={ event => {setTemplateText(event.target.value)}} rows="5" cols="40"></TextareaAutosize> */}
          {/* <TextField   {...register("templateText",{required: "Input required", maxLength : { value: 160, message: "Maximum 160 characters"} })}/> */}
          <TextareaAutosize
            label="Template Text"
            rows="5"
            cols="40"
            variant="outlined"
            type="Text"
            {...register("templateText", {
              required: "Input required",
              maxLength: { value: 160, message: "Maximum 160 characters" },
            })}
          />
          {errors.templateText && errors.templateText.message}
          <br />
          <ButtonGroup>
            {/* <Button variant="contained" onClick={saveHandler}>Save</Button> */}
            <Button
              size="small"
              color="primary"
              variant="contained"
              onClick={handleSubmit(saveHandler)}
            >
              Save
            </Button>

            <Button
              size="small"
              color="primary"
              variant="contained"
              type="submit"
              onClick={cancelHandler}
            >
              Cancel
            </Button>
          </ButtonGroup>
          <br />
        </FormControl>
      </Container>
    </React.Fragment>
  );
}

export default SMSTemplate;