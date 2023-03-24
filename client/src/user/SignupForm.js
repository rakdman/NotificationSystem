import React, { useContext, useState } from "react";

import logo from "../media/logo.png";
import { AuthContext } from "../components/app-context/AuthContext";
import { useHistory } from "react-router-dom";
import axios from "axios";
import {
  FormControl,
  TextField,
  InputLabel,
  TextareaAutosize,
  Button,
  Typography,
  ButtonGroup,
  Avatar,
  Paper,
  Grid,
} from "@material-ui/core";
import Container from "@material-ui/core/Container";
import LockOutlinedIcon from "@material-ui/icons/LockOutlined";
import HomeHeaderPublic from "../components/home/HomeHeaderPublic";
import { useForm } from "react-hook-form";
import ReactDom from "react-dom";
import swal from "sweetalert";

function SignupForm() {
  let usehistory = useHistory();

  const [userName, setUserName] = useState("");
  const [userPassword, setUserPassword] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm();

  const [errorMessage, setErrorMessage] = useState();

  let loginSubmitHandler;

  const onSubmit = (event) => {
    axios
      .post(`http://localhost:9090/api/user/create`, {
        userName: event.userName,
        password: event.userPassword,
        email: event.email,
        firstName: event.firstName,
        lastName: event.lastName,
      })
      .then((res) => {
        console.log(res);
        if (res.status != 200)
          setErrorMessage(<Typography color="error"></Typography>);
      });

    swal({
      title: "Success",
      text: "Success! awaiting admin approval",
      icon: "success",
    });

    usehistory.push("/loginform");
  };

  return (
    <Container maxWidth="sm">
      <HomeHeaderPublic />
      <br />
      <br />
      <br />

      <br />
      <br />

      <Paper elevation={2} variant="outlined">
        <center>
          <FormControl>
            <br />
            <center>
              <Avatar src="/logo3.png">
                {/* <img src={logo} className="App-logo" alt="logo"  width="300" /> */}
              </Avatar>
              <br />
            </center>

            {/* <TextField label="First Name"  variant= "outlined" type="Text" value={firstName} onChange={event => {setFirstName(event.target.value) }}></TextField> */}
            <TextField
              label="First Name"
              variant="outlined"
              type="Text"
              {...register("firstName", { required: "Input required" })}
            />
            {errors.firstName && errors.firstName.message}
            <br />
            {/* <TextField label="Last Name"  variant= "outlined" type="Text" value={lastName} onChange={event => {setLastName(event.target.value) }}></TextField> */}
            <TextField
              label="Last Name"
              variant="outlined"
              type="Text"
              {...register("lastName", { required: "Input required" })}
            />
            {errors.lastName && errors.lastName.message}
            <br />

            <TextField
              label="Email"
              variant="outlined"
              type="Text"
              {...register("email", {
                required: "Input required",
                pattern: {
                  value: /\S+@\S+\.\S+/,
                  message: "Enter valid email",
                },
              })}
            />
            {errors.email && errors.email.message}
            <br />

            <TextField
              label="User Name"
              variant="outlined"
              type="Text"
              {...register("userName", {
                required: "Input required",
                pattern: {
                  value: /[a-zA-Z0-9]/,
                  message: "Enter valid username",
                },
              })}
            />
            {errors.userName && errors.userName.message}
            <br />

            <TextField
              label="Password"
              variant="outlined"
              type="Password"
              {...register("userPassword", {
                required: "Input required",
                minLength: {
                  value: 5,
                  message: "Minimum 5 characters required",
                },
              })}
            />
            {errors.userPassword && errors.userPassword.message}
            <br />
            <Button
              variant="contained"
              type="submit"
              color="primary"
              onClick={handleSubmit(onSubmit)}
            >
              Sign Up
            </Button>
            <br />

            {errorMessage}
            <br />
          </FormControl>
        </center>
      </Paper>
    </Container>
  );
}

export default SignupForm;
