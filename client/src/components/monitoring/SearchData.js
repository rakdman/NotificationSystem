import {
  Button,
  ButtonGroup,
  FormControl,
  Grid,
  TextField,
} from "@material-ui/core";
import axios from "axios";
import React, { useContext, useState } from "react";

import { AuthContext } from "../app-context/AuthContext";

export function SearchData({ filterAllData }) {
  //   constatns
  const [firstName, setFirstName] = useState();
  const [lastName, setLastName] = useState();
  const [contactNo, setContactNo] = useState();
  const [emailId, setEmailId] = useState();
  const [instanceData, setInstanceData] = useState(null);
  const [rows, setRows] = useState([]);

  const { isLoggedIn, idd, setIdd, token } = useContext(AuthContext);
  const headers = { Authorization: "Bearer " + token };
  const searchHandler = (event) => {
    console.log("Building Search parameter list");
    var searchCode;
    if (firstName) {
      searchCode = "F";
    } else {
      searchCode = "0";
    }
    if (lastName) {
      searchCode = searchCode + "L";
    } else {
      searchCode = searchCode + "0";
    }
    if (contactNo) {
      searchCode = searchCode + "C";
    } else {
      searchCode = searchCode + "0";
    }
    if (emailId) {
      searchCode = searchCode + "E";
    } else {
      searchCode = searchCode + "0";
    }
    console.log("Search Parameter List:" + searchCode);
  };

  const resetHandler = (event) => {
    setFirstName("");
    setLastName("");
    setContactNo("");
    setEmailId("");
    console.log("From reset handler");
  };

  return (
    <Grid container xs="auto" direction="row" alignItems="flex-start">
      <Grid item>
        <br />
        <center>
          <FormControl>
            <TextField
              size="small"
              label="FirstName"
              variant="outlined"
              type="Text"
              value={firstName}
              onChange={(event) => {
                setFirstName(event.target.value);
              }}
            ></TextField>
            <TextField
              size="small"
              label="LastName"
              variant="outlined"
              type="Text"
              value={lastName}
              onChange={(event) => {
                setLastName(event.target.value);
              }}
            ></TextField>
            <TextField
              size="small"
              label="ContactNo"
              variant="outlined"
              type="Text"
              value={contactNo}
              onChange={(event) => {
                setContactNo(event.target.value);
              }}
            ></TextField>
            <TextField
              size="small"
              label="EmailId"
              variant="outlined"
              type="Text"
              value={emailId}
              onChange={(event) => {
                setEmailId(event.target.value);
              }}
            ></TextField>
            <center>
              <ButtonGroup>
                <Button
                  variant="contained"
                  size="small"
                  color="primary"
                  style={{ padding: "5px 28px" }}
                  onClick={searchHandler}
                  type="submit"
                >
                  Search
                </Button>
                <Button
                  variant="contained"
                  color="primary"
                  style={{ padding: "5px 28px" }}
                  onClick={resetHandler}
                  type="reset"
                >
                  Reset
                </Button>
              </ButtonGroup>
            </center>
            <br />
          </FormControl>
        </center>
      </Grid>
    </Grid>
  );
}

export default SearchData;
