import {Button} from "@material-ui/core";
import React from "react";
import PanToolIcon from '@mui/icons-material/PanTool';
function CustomSuspendWF () {



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
<PanToolIcon/>
        </Button>
    );
}

export default CustomSuspendWF;