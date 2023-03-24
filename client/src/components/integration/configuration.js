import React, { useContext, useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import axios from "axios";
import {
  FormControl,
  Button,
  Typography,
  ButtonGroup,
  Table,
  TableRow,
  TableBody,
  TableCell,
  Paper,
  Grid,
} from "@material-ui/core";
import Container from "@material-ui/core/Container";
import { AuthContext } from "../app-context/AuthContext";
// import HomeHeader from '../home/HomeHeader';
// import HomeHeaderPublic from '../home/HomeHeaderPublic';
import swal from "sweetalert";

function Configuration() {
  let usehistory = useHistory();

  // const {isLoggedIn,idd,setIdd,role} = useContext(AuthContext);
  const { isLoggedIn, role, token } = useContext(AuthContext);

  if (!isLoggedIn) {
    usehistory.push("/configuration");
  }

  // console.log(role)
  const [instanceData, setInstanceData] = useState(null);
  // const [refresh,setRefresh] = useState();

  // var apiURL = "api/user/readallusers";
  var apiURL = "http://localhost:9090/api/user/getall";
  console.log(apiURL);
  const headers = { Authorization: "Bearer " + token };
  console.log(`Bearer ${token}`);
  const fetchData = async () => {
    const response = await axios.get(apiURL, { headers });
    console.log(response.data);
    // if (role === "1") setInstanceData(response.data);
    setInstanceData(response.data);
  };

  useEffect(() => {
    fetchData();
  }, []);

  const approveHandler = (username) => {
    //  console.log(username)

    //  console.log('Approve button clicked')
    const headers = { Authorization: "Bearer " + token };
    console.log(headers);
    console.log(username);
    async function abc() {
      await axios
        .patch(
          `http://localhost:9090/api/user/update`,
          {
            userName: username,
            approved: true,
          },
          { headers }
        )
        .then((res) => {
          console.log(res);
          fetchData();
        });
    }

    abc();

    swal({
      title: "Approved",
      text: "User approved successfully",
      icon: "success",
    });
    // usehistory.push("/HomeHeader")
    // usehistory.push("/configuration")
  };

  const lockHandler = (username) => {
    //  console.log(event.username)
    //  console.log(username)

    //  console.log('Lock button clicked')

    async function abc() {
      const headers = { Authorization: "Bearer " + token };

      console.log(headers);
      await axios
        .patch(
          `http://localhost:9090/api/user/update`,
          {
            userName: username,
            approved: false,
          },
          { headers }
        )
        .then((res) => {
          console.log(res);
          fetchData();
        });
    }

    abc();

    swal({
      title: "Locked",
      text: "User locked successfully",
      icon: "success",
    });

    // usehistory.push("/loginform")
  };

  return (
    <React.Fragment>
      <br />

      <Grid item xs={10} sm={6}>
        <Container width="40%">
          <Paper elevation={2} style={{ width: "250%" }}>
            <Typography variant="h6">Users</Typography>
            <FormControl>
              <br />

              <Table style={{ width: "120%" }}>
                <TableBody>
                  <TableRow>
                    <TableCell size="medium">
                      {" "}
                      <Typography variant="subtitle1">
                        FirstName
                      </Typography>{" "}
                    </TableCell>
                    <TableCell size="medium">
                      {" "}
                      <Typography variant="subtitle1">LastName</Typography>{" "}
                    </TableCell>
                    <TableCell size="medium">
                      {" "}
                      <Typography variant="subtitle1">Email</Typography>{" "}
                    </TableCell>
                    <TableCell size="medium">
                      {" "}
                      <Typography variant="subtitle1">UserName</Typography>{" "}
                    </TableCell>
                    <TableCell size="medium">
                      {" "}
                      <Typography variant="subtitle1">Role</Typography>{" "}
                    </TableCell>
                    <TableCell size="medium">
                      {" "}
                      <Typography variant="subtitle1">Approved</Typography>{" "}
                    </TableCell>
                  </TableRow>

                  {instanceData &&
                    instanceData.map((item, index) => {
                      return (
                        <TableRow key={item._id}>
                          <TableCell size="medium">
                            {" "}
                            {item.firstName}{" "}
                          </TableCell>
                          <TableCell size="medium"> {item.lastName} </TableCell>
                          <TableCell size="medium"> {item.email} </TableCell>
                          <TableCell size="medium"> {item.userName} </TableCell>
                          <TableCell size="medium">
                            {" "}
                            {item.role === "1" ? "Admin" : "Normal"}{" "}
                          </TableCell>
                          <TableCell size="medium">
                            {" "}
                            {item.approved === true ? "Yes" : "No"}{" "}
                          </TableCell>

                          <ButtonGroup>
                            {/* <Button style= {{padding: "5px 10px"}} onClick ={()=> setUserName(item.username)}  variant="contained" fullWidth='false'>Select</Button> */}
                            {item.userName != "rakdman" && (
                              <Button
                                color="primary"
                                size="small"
                                style={{ padding: "5px 10px" }}
                                onClick={(e) =>
                                  approveHandler(item.userName, e)
                                }
                                variant="contained"
                                fullWidth="false"
                              >
                                Approve
                              </Button>
                            )}
                            {/* <Button style= {{padding: "5px 25px"}} onClick ={lockHandler}  variant="contained" fullWidth='false'>Lock</Button>     */}
                            {item.userName != "rakdman" && (
                              <Button
                                color="primary"
                                size="small"
                                style={{ padding: "5px 25px" }}
                                onClick={(e) => lockHandler(item.userName, e)}
                                variant="contained"
                                fullWidth="false"
                              >
                                Lock
                              </Button>
                            )}
                          </ButtonGroup>
                        </TableRow>
                      );
                    })}
                </TableBody>

                <br />
                <br />
                <br />
              </Table>
            </FormControl>
          </Paper>
        </Container>
      </Grid>
    </React.Fragment>
  );
}

export default Configuration;
