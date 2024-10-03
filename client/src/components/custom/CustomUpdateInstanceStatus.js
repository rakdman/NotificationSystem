import {Button} from "@material-ui/core";
import React from "react";
import CancelIcon from '@mui/icons-material/Cancel';
function CustomUpdateInstanceStatus () {



    return (
        <Button
            color="primary"
            size="small"
            style={{margin: "5px 5px"}}
            variant="contained"
            // onClick={() => {
            //     setOpen(true);
            // }}
            fullWidth="false"
        >
            <CancelIcon/>
        </Button>
    );
}

export default CustomUpdateInstanceStatus;