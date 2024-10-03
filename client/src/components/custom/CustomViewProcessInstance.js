import {Button} from "@material-ui/core";
import Modal from "@material-ui/core/Modal";
import React, {useState} from "react";
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import ViewInstanceSteps from "../monitoring/ViewInstanceSteps";
import ListIcon from "@mui/icons-material/List";

function CustomViewProcessInstance() {

    const [open, setOpen] = useState(false)
    const handleClose = () => {
        setOpen(false);
    };

    return (
        <>
            <Button
                color="primary"
                size="small"
                style={{margin: "5px 5px"}}
                variant="contained"
                onClick={() => {
                    setOpen(true);
                }}
                fullWidth="false"
            >
                <ListIcon/>
            </Button>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="simple-modal-title"
                aria-describedby="simple-modal-description"
            >
                <Dialog
                    maxWidth="md"
                    open="open"
                    onClose={handleClose}
                    style={{
                        minHeight: "95vh",
                        maxHeight: "95vh",
                    }}
                >
                    <DialogTitle>Notification Steps</DialogTitle>
                    <DialogContent>
                        <ViewInstanceSteps/>
                        <Button variant="contained"
                                size="small"
                                color="primary"
                                style={{padding: "5px 28px", marginRight: "auto"}}
                                onClick={handleClose}>
                            Cancel
                        </Button>
                    </DialogContent>
                </Dialog>
            </Modal>
        </>
    );
}

export default CustomViewProcessInstance;