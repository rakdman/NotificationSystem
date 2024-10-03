import React from "react";
import {TableCell} from "@material-ui/core";
function CustomTableValueCell({value}) {
    return (
        <TableCell size="small">
            {value}
        </TableCell>
    );
}

export default CustomTableValueCell;