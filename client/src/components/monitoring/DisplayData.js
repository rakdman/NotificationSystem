import React, { useContext, useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import axios from "axios";
import SearchBox from "./SearchData";

import {
  FormControl,
  TableHead,
  Typography,
  ButtonGroup,
  Table,
  TableRow,
  TableBody,
  TableCell,
  Paper,
  Grid,
  TablePagination,
} from "@material-ui/core";
import Container from "@material-ui/core/Container";

import { AuthContext } from "../app-context/AuthContext";

import CustomTableHeaderCell from "../custom/CustomTableHeaderCell";
import CustomTableValueCell from "../custom/CustomTableValueCell";
import CustomViewProcessInstance from "../custom/CustomViewProcessInstance";
import CustomUpdateInstanceStatus from "../custom/CustomUpdateInstanceStatus";
import CustomReassignWF from "../custom/CustomReassignWF";
import CustomSuspendWF from "../custom/CustomSuspendWF";
import CustomEditData from "../custom/EditData";
function DisplayData() {
  let usehistory = useHistory();
  const { isLoggedIn, token } = useContext(AuthContext);
  const headers = { Authorization: "Bearer " + token };
  const [rows, setRows] = useState([]);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(15);
  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };
  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 15));
    setPage(0);
  };
  const emptyRows =
    rowsPerPage - Math.min(rowsPerPage, rows.length - page * rowsPerPage);

  if (!isLoggedIn) {
    usehistory.push("/");
  }

  const [instanceData, setInstanceData] = useState(null);

  useEffect(() => {
    fetchData();
  }, []);

  const [open, setOpen] = React.useState(false);

  const handleClose = () => {
    setOpen(false);
  };

  const apiURL = "http://localhost:9090/api/instances/readallinstances";

  const fetchData = async () => {
    const response = await axios.get(apiURL, { headers });
    setInstanceData(response.data);
    setRows(response.data);
    console.log(response.data);
  };

  const [filteredData, setFilteredData] = useState();
  const filterAllData = (data) => {
    setFilteredData(data);
  };

  const url = "http://localhost:9090/api/instances/readoneinstancesbyparams";

  return (
    <>
      {/* Search Block Starts */}
      <SearchBox searchedData={filterAllData} />
      {/* Search Block Ends */}

      <Grid item>
        <Container width="40%">
          <Paper elevation={2} style={{ width: "131%" }}>
            <Typography variant="h6">Instance View</Typography>
            <FormControl>
              <Table style={{ width: "100%" }}>
                <TableHead style={{ background: "royalblue", text: "white" }}>
                  <TableRow>
                    <CustomTableHeaderCell name="First Name" color="white" />
                    <CustomTableHeaderCell name="Last Name" color="white" />
                    <CustomTableHeaderCell name="Contact No" color="white" />
                    <CustomTableHeaderCell name="Email Id" color="white" />
                    <CustomTableHeaderCell name="Workflow Name" color="white" />
                    <CustomTableHeaderCell name="Bill Id" color="white" />
                    <CustomTableHeaderCell name="Open Amount" color="white" />
                    <CustomTableHeaderCell
                      name="Instance Status"
                      color="white"
                    />
                    <CustomTableHeaderCell name="Entry Date" color="white" />
                    <CustomTableHeaderCell name="Next Step" color="white" />
                    <CustomTableHeaderCell
                      name="Next Step Date"
                      color="white"
                    />
                    <CustomTableHeaderCell
                      name="Assigned Agent"
                      color="white"
                    />
                  </TableRow>
                </TableHead>
                <TableBody>
                  {rows
                    .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                    .map((item) => {
                      return (
                        <TableRow key={item.instanceId}>
                          <CustomTableValueCell value={item.firstName} />
                          <CustomTableValueCell value={item.lastName} />
                          <CustomTableValueCell value={item.contactNo} />
                          <CustomTableValueCell value={item.emailID} />
                          <CustomTableValueCell value={item.workflowName} />
                          <CustomTableValueCell value={item.billId} />
                          <CustomTableValueCell value={item.openAmount} />
                          <CustomTableValueCell value={item.instanceStatus} />
                          <CustomTableValueCell value={item.nextStep} />
                          <CustomTableValueCell value={item.nextStepdate} />
                          <CustomTableValueCell value={item.assigendAgent} />
                          <CustomTableValueCell value={item.entrydate} />

                          <ButtonGroup>
                            <CustomViewProcessInstance />
                            <CustomReassignWF />
                            <CustomSuspendWF />
                            <CustomUpdateInstanceStatus />
                            <CustomEditData />
                          </ButtonGroup>
                        </TableRow>
                      );
                    })}
                </TableBody>

                {emptyRows > 0 && (
                  <TableRow style={{ height: 40 * emptyRows }}>
                    <TableCell />
                  </TableRow>
                )}
              </Table>
              <Table style={{ width: "100%" }}>
                <TablePagination
                  // rowsPerPageOptions={[5, 10, 25]}
                  rowsPerPageOptions={[15]}
                  component="div"
                  count={rows.length}
                  rowsPerPage={rowsPerPage}
                  page={page}
                  onChangePage={handleChangePage}
                  onChangeRowsPerPage={handleChangeRowsPerPage}
                />
              </Table>
            </FormControl>
          </Paper>
        </Container>
      </Grid>
    </>
  );
}

export default DisplayData;
