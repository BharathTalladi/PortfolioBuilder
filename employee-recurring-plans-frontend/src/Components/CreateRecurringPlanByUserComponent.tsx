import { useState } from "react";
import { Container,Box, Typography,Button,TextField,Avatar} from "@mui/material";
import EditIcon from '@mui/icons-material/Edit';
import { NavLink } from "react-router-dom";

const CreateRecurringPlanByUserComponent = ()=>{

    const[id,setId]=useState('');
    const[salary,setSalary]=useState('')
    const[dob,setDob]=useState('')
    const[self_contribution_amount_401K,setSelf_Contribution_Amount_401K]=useState('');
    const[self_contribution_amount_HSA,setSelf_Contribution_Amount_HSA]=useState('');
    const[self_contribution_amount_FSA,setSelf_Contribution_Amount_FSA]=useState('');
    const[self_contribution_amount_ROTHIRA,setSelf_Contribution_Amount_ROTHIRA]=useState('');

    return(
        <>
            <Container maxWidth="xs">
        <Box sx={{mt:10,display: "flex",flexDirection: "column",alignItems: "center",}}>
        <Avatar sx={{ m: 1, bgcolor: "primary.light" }}>
        <EditIcon />
        </Avatar>
        <Typography variant="h5">Update Self Contribution Details</Typography>
            <Box>
            <TextField value={id} name='id' label="Enter Employee ID" sx={{ m: 1 }} id="id"  margin="normal" fullWidth autoFocus onChange={(s)=> setId(s.target.value)}/>
            <TextField value={dob} name='dob' label="Enter Date of Birth" sx={{ m: 1 }} id="dob"  margin="normal" fullWidth autoFocus onChange={(s)=> setDob(s.target.value)}/>
                    <TextField value={salary} name='salary' label="Edit Salary" sx={{ m: 1 }} id="salary"  margin="normal" fullWidth autoFocus onChange={(s)=> setSalary(s.target.value)}/>
                    <TextField value={self_contribution_amount_401K} name='self_contribution_amount_401K' label="Enter Self 401k Contribution" 
                      sx={{ m: 1 }} id="self_contribution_amount_401K"margin="normal" fullWidth autoFocus onChange={(s)=> setSelf_Contribution_Amount_401K(s.target.value)}/>
                    <TextField value={self_contribution_amount_HSA} name='self_contribution_amount_HSA' label="Enter Self HSA Contribution" 
                      sx={{ m: 1 }} id="self_contribution_amount_401K"  margin="normal" fullWidth autoFocus onChange={(s)=> setSelf_Contribution_Amount_HSA(s.target.value)}/>
                    <TextField value={self_contribution_amount_FSA} name='self_contribution_amount_FSA' label="Enter Self FSA Contribution" 
                      sx={{ m: 1}} id="self_contribution_amount_401K" margin="normal" fullWidth autoFocus onChange={(s)=> setSelf_Contribution_Amount_FSA(s.target.value)}/>
                    <TextField value={self_contribution_amount_ROTHIRA} name='self_contribution_amount_ROTHIRA' label="Enter Self ROTH IRA Contribution" 
                      sx={{ m: 1 }} id="self_contribution_amount_401K" margin="normal" fullWidth autoFocus onChange={(s)=> setSelf_Contribution_Amount_ROTHIRA(s.target.value)}/> 
                    <Button sx={{ mt:0.8, marginLeft: "12px",backgroundColor: "#FFF" }} variant="contained"><NavLink to={`/getUserPlanById/${id}`}>Save</NavLink></Button>
            </Box>
        </Box>
        </Container>
        </>
    )
}

export default CreateRecurringPlanByUserComponent;