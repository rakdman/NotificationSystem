import {Button} from "@material-ui/core";
import React from "react";
import MoveDownIcon from '@mui/icons-material/MoveDown';

function CustomReassignWF () {



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
            <MoveDownIcon/>
        </Button>
    );
}

export default CustomReassignWF;