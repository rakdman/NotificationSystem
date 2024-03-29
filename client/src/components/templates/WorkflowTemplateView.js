import React, { useContext, useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import axios from "axios";
import swal from "sweetalert";
import HomeHeader from "../home/HomeHeader";
import ViewWorkflowSteps from "./ViewWorkflowSteps";

import {
  FormControl,
  Button,
  Typography,
  Table,
  TableRow,
  TableBody,
  TableCell,
} from "@material-ui/core";
import Container from "@material-ui/core/Container";

import Modal from "@material-ui/core/Modal";

import { AuthContext } from "../app-context/AuthContext";
import { ContactSupportOutlined } from "@material-ui/icons";

import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";

function WorkflowTemplateView() {
  const usehistory = useHistory();

  let submitHandler;

  const [firstName, setFirstName] = useState();
  const [lastName, setLastName] = useState();
  const [workflowTemplateStep, setworkflowTemplateStep] = useState();
  const { isLoggedIn, idd, setIdd } = useContext(AuthContext);


  if (!isLoggedIn) {
    usehistory.push("/");
  }

  let nameInput, textInput, message;



  const [wfData, setWfData] = useState(null);
  const apiURL = "http://localhost:9090/api/workflow/readallwftemplate";
  const fetchData = async () => {
    const response = await axios.get(apiURL);
    setWfData(response.data);
  };

  useEffect(() => {
    fetchData();
  }, []);

  let saveHandler;

  // return(<>
  //     {wfData && wfData.map((item,index)=> {console.log(item.)})}

  // </>)

  // ViewHandler and Modal

  const [open, setOpen] = React.useState(false);

  const viewHandler = (event) => {};

  const handleClose = () => {
    setOpen(false);
  };

  const body = (
    <Container>
      <Dialog open="open" onClose={handleClose}>
        <DialogTitle>Workflow Steps</DialogTitle>
        <DialogContent>
          <ViewWorkflowSteps />
        </DialogContent>
      </Dialog>
    </Container>
  );

  return (
    <Container>
      {/* <HomeHeader/> */}

      <Container width="40%">
        <Typography variant="h6">Workflow View</Typography>
        <FormControl>
          <Table style={{ width: "100%" }}>
            <TableBody>
              <TableRow>
                <TableCell size="medium">
                  {" "}
                  <Typography variant="subtitle1">Workflow ID</Typography>{" "}
                </TableCell>
                <TableCell size="medium">
                  {" "}
                  <Typography variant="subtitle1">
                    Workflow Name
                  </Typography>{" "}
                </TableCell>
              </TableRow>

              {wfData &&
                wfData.map((item, index) => {
                  return (
                    <TableRow key={item.workflowTemplateId}>
                      {console.log(item)}
                      <TableCell size="medium">
                        {" "}
                        {item.workflowTemplateId}{" "}
                      </TableCell>
                      <TableCell size="medium">
                        {" "}
                        {item.workflowTemplateName}{" "}
                      </TableCell>
                      {/*<TableCell size="medium">*/}
                      {/*  {" "}*/}
                        {item.workflowDefinitionStep.map((step) => {
                          console.log(step.workflowTemplateStepName);
                          console.log(step.workflowTemplateStepWait);
                        })}
                      {/*{" "}*/}
                      {/*</TableCell>*/}

                      <Button
                        color="primary"
                        variant="contained"
                        size="small"
                        onClick={() => {
                          setOpen(true);
                          setIdd(item.workflowTemplateId);
                        }}
                        fullWidth="false"
                      >
                        View
                      </Button>
                      <Modal
                        open={open}
                        onClose={handleClose}
                        aria-labelledby="simple-modal-title"
                        aria-describedby="simple-modal-description"
                      >
                        {body}
                      </Modal>
                    </TableRow>
                  );
                })}
            </TableBody>
          </Table>
        </FormControl>
      </Container>
    </Container>
  );
}

export default WorkflowTemplateView;
