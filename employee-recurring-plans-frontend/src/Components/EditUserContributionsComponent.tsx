import { useState } from "react";
import { useParams,NavLink } from "react-router-dom";
import { editUserContributions } from "../Service/Service";
import { Container,Box, Typography,Button,TextField,Avatar} from "@mui/material";
import EditIcon from '@mui/icons-material/Edit';

const EditUserContributionsComponent=()=>{

    const {id} = useParams<{id?:string}>();
    const [salary,setSalary]=useState('');
    const [self_contribution_amount_401K,setSelf_Contribution_Amount_401K]=useState('');
    const [self_contribution_amount_HSA,setSelf_Contribution_Amount_HSA]=useState('');
    const [self_contribution_amount_FSA,setSelf_Contribution_Amount_FSA]=useState('');
    const [self_contribution_amount_ROTHIRA,setSelf_Contribution_Amount_ROTHIRA]=useState('');

    const editUserContributions = (u: React.MouseEvent<HTMLButtonElement>) =>{
        u.preventDefault();
        const editUserCOntributionDetails={salary,self_contribution_amount_401K,self_contribution_amount_HSA,self_contribution_amount_FSA,self_contribution_amount_ROTHIRA};
        console.log(editUserCOntributionDetails)
    }

    return(
        <Container maxWidth="xs">
        <Box sx={{mt:10,display: "flex",flexDirection: "column",alignItems: "center",}}>
        <Avatar sx={{ m: 1, bgcolor: "primary.light" }}>
        <EditIcon />
        </Avatar>
        <Typography variant="h5">Update Self Contribution Details</Typography>
            <Box>
                    <TextField value={salary} name='salary' label="Edit Salary" sx={{ m: 1 }} id="salary"  margin="normal" fullWidth autoFocus onChange={(s)=> setSalary(s.target.value)}/>
                    <TextField value={self_contribution_amount_401K} name='self_contribution_amount_401K' label="Edit Self 401k Contribution" 
                      sx={{ m: 1 }} id="self_contribution_amount_401K"margin="normal" fullWidth autoFocus onChange={(s)=> setSelf_Contribution_Amount_401K(s.target.value)}/>
                    <TextField value={self_contribution_amount_HSA} name='self_contribution_amount_HSA' label="Edit Self HSA Contribution" 
                      sx={{ m: 1 }} id="self_contribution_amount_401K"  margin="normal" fullWidth autoFocus onChange={(s)=> setSelf_Contribution_Amount_HSA(s.target.value)}/>
                    <TextField value={self_contribution_amount_FSA} name='self_contribution_amount_FSA' label="Edit Self FSA Contribution" 
                      sx={{ m: 1}} id="self_contribution_amount_401K" margin="normal" fullWidth autoFocus onChange={(s)=> setSelf_Contribution_Amount_FSA(s.target.value)}/>
                    <TextField value={self_contribution_amount_ROTHIRA} name='self_contribution_amount_ROTHIRA' label="Edit Self ROTH IRA Contribution" 
                      sx={{ m: 1 }} id="self_contribution_amount_401K" margin="normal" fullWidth autoFocus onChange={(s)=> setSelf_Contribution_Amount_ROTHIRA(s.target.value)}/> 
                    <Button sx={{ mt:0.8,marginLeft: "auto",backgroundColor: "#FFF" }} variant="contained" onClick={(u)=> editUserContributions(u)}><NavLink to={`/getUserPlanById/${id}`}>Save</NavLink></Button>
                    <Button sx={{ mt:0.8, marginLeft: "12px",backgroundColor: "#FFF" }} variant="contained"><NavLink to={`/getUserPlanById/${id}`}>Cancel</NavLink></Button>
            </Box>
        </Box>
        </Container>
    )
}

export default EditUserContributionsComponent;