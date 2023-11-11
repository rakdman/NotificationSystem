import React from "react";
import { useHistory } from "react-router-dom";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Button from "@material-ui/core/Button";
import IconButton from "@material-ui/core/IconButton";
import Container from "@material-ui/core/Container";

export default function HomeHeaderPublic() {
  let usehistory = useHistory();

  const login = () => {
    usehistory.push("/loginform");
  };

  const SignupFormHandler = () => {
    console.log("From signnup handler");
    usehistory.push("/signup");
  };

  return (
    <Container>
      <AppBar position="fixed" color="primary">
        <Toolbar variant="regular">
          <div>
            <IconButton color="inherit" aria-label="menu">
              Debit Notification System
            </IconButton>

            <Button
              color="inherit"
              onClick={login}
              style={{ textTransform: "capitalize" }}
            >
              Login
            </Button>
            <Button
              color="inherit"
              onClick={SignupFormHandler}
              style={{ textTransform: "capitalize" }}
            >
              SignUp
            </Button>
          </div>
        </Toolbar>
      </AppBar>

      <div></div>
    </Container>
  );
}
