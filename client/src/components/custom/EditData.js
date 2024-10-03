import {Button} from "@material-ui/core";
import React from "react";
import EditIcon from '@mui/icons-material/Edit';
function CustomEditData () {



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
            <EditIcon/>
        </Button>
    );
}

export default CustomEditData;