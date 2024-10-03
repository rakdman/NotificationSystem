import React from "react";
import {TableCell, Typography} from "@material-ui/core";



function CustomTableHeaderCell({name, color}) {
    return (
       <TableCell size="small" style={{color:color}}>
            <Typography variant="subtitle2">
                {name}
            </Typography>
        </TableCell>
    );
}

export default CustomTableHeaderCell;