import { useState } from "react";
import { Container,Box, Typography,Button,TextField,Avatar} from "@mui/material";
import EditIcon from '@mui/icons-material/Edit';
import { NavLink } from "react-router-dom";
import { createUserPlanByEmployer } from "../Service/Service";

interface CreateUserPlanByEmployer{
    id:string,
    employer_contribution_limit_401k:number,
    employer_contribution_limit_HSA:number
    employer_contribution_limit_FSA:number
    employer_contribution_limit_ROTHIRA: number
}




const CreateUserRecurringPlanByEmployerComponent = ({userId}: {userId: string})=>{
    const[id]=useState(userId);
    const[employer_contribution_limit_401k,setEmployer_Contribution_Limit_401k]=useState<number>(0.0);
    const[employer_contribution_limit_HSA,setEmployer_Contribution_Limit_HSA]=useState<number>(0.0);
    const[employer_contribution_limit_FSA,setEmployer_Contribution_Limit_FSA]=useState<number>(0.0);
    const[employer_contribution_limit_ROTHIRA,setEmployer_Contribution_Limit_ROTHIRA]=useState<number>(0.0);

    const createUserRecurringPlanByEmployer = async () => {
        try {
          const createUserPlanByEmployerData: CreateUserPlanByEmployer = {
            id,
            employer_contribution_limit_401k,
            employer_contribution_limit_HSA,
            employer_contribution_limit_FSA,
            employer_contribution_limit_ROTHIRA
          };
    
          const response=await createUserPlanByEmployer(createUserPlanByEmployerData);
          console.log(response.data);
        } catch (error) {
          console.error(error);
        }
      };


    return(
        <Container maxWidth="xs">
        <Box sx={{mt:10,display: "flex",flexDirection: "column",alignItems: "center",}}>
        <Avatar sx={{ m: 1, bgcolor: "primary.light" }}>
        <EditIcon />
        </Avatar>
        <Typography variant="h5">Update Self Contribution Details</Typography>
            <Box>
                    <TextField value={id} name='id' label="Employee ID" sx={{ m: 1 , fontWeight:'bold'}} id="id"  margin="normal" fullWidth autoFocus disabled/>
                    <TextField value={employer_contribution_limit_401k} name='self_contribution_amount_401K' label="Edit Self 401k Contribution" 
                      sx={{ m: 1 }} id="self_contribution_amount_401K"margin="normal" fullWidth autoFocus onChange={(s)=> setEmployer_Contribution_Limit_401k(Number(s.target.value))}/>
                    <TextField value={employer_contribution_limit_HSA} name='self_contribution_amount_HSA' label="Edit Self HSA Contribution" 
                      sx={{ m: 1 }} id="self_contribution_amount_401K"  margin="normal" fullWidth autoFocus onChange={(s)=> setEmployer_Contribution_Limit_HSA(Number(s.target.value))}/>
                    <TextField value={employer_contribution_limit_FSA} name='self_contribution_amount_FSA' label="Edit Self FSA Contribution" 
                      sx={{ m: 1}} id="self_contribution_amount_401K" margin="normal" fullWidth autoFocus onChange={(s)=> setEmployer_Contribution_Limit_FSA(Number(s.target.value))}/>
                    <TextField value={employer_contribution_limit_ROTHIRA} name='self_contribution_amount_ROTHIRA' label="Edit Self ROTH IRA Contribution" 
                      sx={{ m: 1 }} id="self_contribution_amount_401K" margin="normal" fullWidth autoFocus onChange={(s)=> setEmployer_Contribution_Limit_ROTHIRA(Number(s.target.value))}/> 
                    <Button sx={{ mt:0.8,marginLeft: "auto",backgroundColor: "#FFF" }} variant="contained" onClick={createUserRecurringPlanByEmployer}><NavLink to={`/getUserPlanById/${id}`}>Save</NavLink></Button>
                    <Button sx={{ mt:0.8, marginLeft: "12px",backgroundColor: "#FFF" }} variant="contained"><NavLink to={`/getAllUsersPlan`}>Cancel</NavLink></Button>
            </Box>
        </Box>
        </Container>
    );
};
export default CreateUserRecurringPlanByEmployerComponent;