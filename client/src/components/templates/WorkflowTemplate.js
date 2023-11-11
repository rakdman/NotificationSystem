import React, { useContext, useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import axios from "axios";
import swal from "sweetalert";

import {
  FormControl,
  TextField,
  TextareaAutosize,
  Button,
  Typography,
  ButtonGroup,
  Table,
  TableBody,
  TableRow,
  TableCell,
  Paper,
} from "@material-ui/core";
import Container from "@material-ui/core/Container";
import Autocomplete from "@material-ui/lab/Autocomplete";
// import NewRow from './NewRow';
import { useForm } from "react-hook-form";

import { AuthContext } from "../app-context/AuthContext";
import { CenterFocusStrong } from "@material-ui/icons";

function WorkflowTemplate() {
  const usehistory = useHistory();

  let submitHandler;

  const [workflowName, setworkflowName] = useState();
  // const [templateText,setTemplateText] = useState();
  // const [newWFStep,setNewWFtep] = useState([]);
  // const [templateValues, setTemplateValues] = useState([])

  const { isLoggedIn } = useContext(AuthContext);

  // console.log("In Workflow Template");
  // console.log(isLoggedIn);

  if (!isLoggedIn) {
    usehistory.push("/");
  }

  // let nameInput, textInput,message;

  const onSubmit = (event) => {
    // event.preventDefault();
    // console.log("Workflow Save submitHandler");
    // console.log(event)

    // const payload = JSON.stringify({tname:templateName, text:templateText });
    // var steps=[{workflowTemplateStepWait:number1,workflowTemplateStepName:select1},{workflowTemplateStepWait:number2,workflowTemplateStepName:select2},{workflowTemplateStepWait:number3,workflowTemplateStepName:select3},{workflowTemplateStepWait:number4,workflowTemplateStepName:select4},{workflowTemplateStepWait:number5,workflowTemplateStepName:select5}]

    var steps = [];

    if (event.number1 && event.select1)
      steps.push({
        workflowTemplateStepName: event.select1,
        workflowTemplateStepWait: event.number1,
      });

    if (event.number2 && event.select2 && steps)
      steps.push({
        workflowTemplateStepName: event.select2,
        workflowTemplateStepWait: event.number2,
      });

    if (event.number3 && event.select3 && steps)
      steps.push({
        workflowTemplateStepName: event.select3,
        workflowTemplateStepWait: event.number3,
      });

    if (event.number4 && event.select4 && steps)
      steps.push({
        workflowTemplateStepName: event.select4,
        workflowTemplateStepWait: event.number4,
      });

    if (event.number5 && event.select5 && steps)
      steps.push({
        workflowTemplateStepName: event.select5,
        workflowTemplateStepWait: event.number5,
      });
    // axios.post(`http://localhost:9090/api/workflow/create`,{workflowTemplateName:event.workflowName,workflowDefinitionStep:[{workflowTemplateStepWait:number1,workflowTemplateStepName:select1},{workflowTemplateStepWait:number2,workflowTemplateStepName:select2},{workflowTemplateStepWait:number3,workflowTemplateStepName:select3},{workflowTemplateStepWait:number4,workflowTemplateStepName:select4},{workflowTemplateStepWait:number5,workflowTemplateStepName:select5}]})
    axios
      .post(`http://localhost:9090/api/workflow/create`, {
        workflowTemplateName: event.workflowName,
        workflowDefinitionStep: steps,
      })
      .then((res) => {
        console.log(res.data);
      });

    swal({
      title: "Success",
      text: "Workflow template created successfully!",
      icon: "success",
    });

    usehistory.push("/loginform");
  };

  const cancelHandler = (event) => {
    event.preventDefault();
    // console.log("Workflow Cancel submitHandler");
    usehistory.push("/loginform");
  };

  const addNewHandler = (event) => {
    console.log("In addNewHandler");
  };

  const [number1, setNumber1] = useState(null);
  const [select1, setSelect1] = useState(null);
  const [template1, setTemplate1] = useState(null);

  const [number2, setNumber2] = useState(null);
  const [select2, setSelect2] = useState(null);

  const [number3, setNumber3] = useState(null);
  const [select3, setSelect3] = useState(null);

  const [number4, setNumber4] = useState(null);
  const [select4, setSelect4] = useState(null);

  const [number5, setNumber5] = useState(null);
  const [select5, setSelect5] = useState(null);

  const [templateArray, setTemplateArray] = useState();

  const [smsTemp, setBooks] = useState(null);
  // const apiURL = "api/template/readalltemplates";   // This API to populate the SMS templates
  const apiURL = "http://localhost:9090/api/template/emailtemplates";

  const fetchData = async () => {
    var response = await axios.get(apiURL, {
      params: { templateType: "EMAIL" },
    });
    //   console.log(response.data)
    setBooks(response.data);
  };

  // const TempalteList = smsTemp.map((item,index)=>{console.log(item.tname)})

  useEffect(() => {
    fetchData();
  }, []);

  var array = [];

  // useEffect(() => {
  //     console.log(smsTemp);
  //     smsTemp && smsTemp.map((item,index)=> {array.push(item.tname.toString())});
  //     console.log(array);
  //     setTemplateArray(array);
  // }, [])

  var val;

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  return (
    <React.Fragment>
      <br />
      <Container width="70%">
        {/* <FormControl onSubmit={submitHandler} size="medium"> */}
        <FormControl size="medium">
          <Typography variant="subtitle1">Workflow Template</Typography>
          <br />

          {/* <TextField label= "Workflow Name" variant="outlined" type="Text" value={workflowName} onChange={ (event) => {setworkflowName(event.target.value)}}></TextField> */}
          <TextField
            label="Workflow Name"
            variant="outlined"
            type="Text"
            {...register("workflowName", {
              required: "Input required",
              maxLength: { value: 50, message: "Maximum 50 characters" },
            })}
          />
          {errors.workflowName && errors.workflowName.message}
          <br />
          <Paper elevation={2}>
            <Table>
              <TableBody>
                <TableRow>
                  <TableCell>
                    <Typography variant="subtitle1">
                      Trigger After Duration
                    </Typography>
                  </TableCell>
                  <TableCell>
                    <Typography variant="subtitle1">
                      Notification Template
                    </Typography>
                  </TableCell>
                </TableRow>
                {/* {smsTemp && smsTemp.map((item,index)=> {array.push(item.tname)})} */}
                {smsTemp &&
                  smsTemp.map((item, index) => {
                    array.push(item.notificationTemplateName);
                  })}
                <TableRow>
                  <TableCell>
                    {/* <TextField label= "Number" value={number1} onChange={(e)=>{setNumber1(e.target.value)}}></TextField> */}
                    <TextField
                      label="Number"
                      {...register("number1", {
                        required: "Input required",
                        min: {
                          value: 0,
                          message: "Minimum must be at least 0 days",
                        },
                        max: {
                          value: 360,
                          message: "Maximum allowed is 360 days",
                        },
                      })}
                    />
                    {errors.number1 && errors.number1.message}
                    {/* { console.log("number1:"+number1+" select1:"+select1)} */}
                  </TableCell>
                  <TableCell>
                    <Autocomplete
                      onChange={(event, select1) => setSelect1(select1)}
                      options={array}
                      renderInput={(params) => (
                        <TextField
                          {...params}
                          label="Select Template"
                          variant="outlined"
                          {...register("select1", {
                            required: "Input required",
                          })}
                        />
                      )}
                    />
                    {errors.select1 && errors.select1.message}
                  </TableCell>
                </TableRow>

                <TableRow>
                  <TableCell>
                    {/* <TextField label= "Number" value={number2} onChange={(e)=>{setNumber2(e.target.value)}}></TextField> */}
                    <TextField
                      label="Number"
                      {...register("number2", {
                        min: {
                          value: 0,
                          message: "Minimum must be at least 0 days",
                        },
                        max: {
                          value: 360,
                          message: "Maximum allowed is 360 days",
                        },
                      })}
                    />
                    {errors.number2 && errors.number2.message}
                    {/* {console.log("number2:"+number2+" select2"+select2)} */}
                  </TableCell>
                  <TableCell>
                    <Autocomplete
                      onChange={(event, select2) => setSelect2(select2)}
                      options={array}
                      renderInput={(params) => (
                        <TextField
                          {...params}
                          label="Select Template"
                          variant="outlined"
                          {...register("select2")}
                        />
                      )}
                    />
                    {errors.select2 && errors.select2.message}
                  </TableCell>
                </TableRow>

                <TableRow>
                  <TableCell>
                    {/* <TextField label= "Number" value={number3} onChange={(e)=>{setNumber3(e.target.value)}}></TextField> */}
                    <TextField
                      label="Number"
                      {...register("number3", {
                        min: {
                          value: 0,
                          message: "Minimum must be at least 0 days",
                        },
                        max: {
                          value: 360,
                          message: "Maximum allowed is 360 days",
                        },
                      })}
                    />
                    {errors.number3 && errors.number3.message}
                    {/* {console.log("number3:"+number3+" select3"+select1)} */}
                  </TableCell>
                  <TableCell>
                    <Autocomplete
                      onChange={(event, select3) => setSelect3(select3)}
                      options={array}
                      renderInput={(params) => (
                        <TextField
                          {...params}
                          label="Select Template"
                          variant="outlined"
                          {...register("select3")}
                        />
                      )}
                    />
                    {errors.select3 && errors.select3.message}
                  </TableCell>
                </TableRow>

                <TableRow>
                  <TableCell>
                    {/* <TextField label= "Number" value={number4} onChange={(e)=>{setNumber4(e.target.value)}}></TextField> */}
                    <TextField
                      label="Number"
                      {...register("number4", {
                        min: {
                          value: 0,
                          message: "Minimum must be at least 0 days",
                        },
                        max: {
                          value: 360,
                          message: "Maximum allowed is 360 days",
                        },
                      })}
                    />
                    {errors.number4 && errors.number4.message}
                    {/* { console.log("number4:"+number4+" select4"+select1)} */}
                  </TableCell>
                  <TableCell>
                    <Autocomplete
                      onChange={(event, select4) => setSelect4(select4)}
                      options={array}
                      renderInput={(params) => (
                        <TextField
                          {...params}
                          label="Select Template"
                          variant="outlined"
                          {...register("select4")}
                        />
                      )}
                    />
                    {errors.select4 && errors.select4.message}
                  </TableCell>
                </TableRow>

                <TableRow>
                  <TableCell>
                    {/* <TextField label= "Number" value={number5} onChange={(e)=>{setNumber5(e.target.value)}}></TextField> */}
                    <TextField
                      label="Number"
                      {...register("number5", {
                        min: {
                          value: 0,
                          message: "Minimum must be at least 0 days",
                        },
                        max: {
                          value: 360,
                          message: "Maximum allowed is 360 days",
                        },
                      })}
                    />
                    {errors.number5 && errors.number5.message}
                    {/* {console.log("number5"+number5+" select5"+select5)} */}
                  </TableCell>
                  <TableCell>
                    <Autocomplete
                      onChange={(event, select5) => setSelect5(select5)}
                      options={array}
                      renderInput={(params) => (
                        <TextField
                          {...params}
                          label="Select Template"
                          variant="outlined"
                          {...register("select5")}
                        />
                      )}
                    />
                    {errors.select5 && errors.select5.message}
                  </TableCell>
                </TableRow>
              </TableBody>
            </Table>
            {/* <Button variant="contained" onClick= {addNewHandler} >Add New</Button> */}
          </Paper>
          <br />
          {/* <Button variant="contained" fullWidth="false" onClick= {saveHandler} >Save</Button> */}
          <Button
            color="primary"
            size="small"
            variant="contained"
            fullWidth="false"
            onClick={handleSubmit(onSubmit)}
          >
            Save
          </Button>
          <br />
          <br />
        </FormControl>
      </Container>
    </React.Fragment>
  );
}

export default WorkflowTemplate;
