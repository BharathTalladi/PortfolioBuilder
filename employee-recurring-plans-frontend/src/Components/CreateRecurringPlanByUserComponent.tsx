import { useState } from "react";
import { Container,Box, Typography,Button,TextField,Avatar,Grid, FormHelperText} from "@mui/material";
import EditIcon from '@mui/icons-material/Edit';
import { NavLink ,useNavigate} from "react-router-dom";
import { createUserPlan } from "../Service/Service";

const CreateRecurringPlanByUserComponent =()=>{
    
    const userId=localStorage.getItem("authenticatedUser");
    const[dob,setDob]=useState<Date | null>(null)
    const[salary,setSalary]=useState<number>()
    const navigate=useNavigate();
    const[self_contribution_limit_401K,setSelf_Contribution_Limit_401K]=useState<number>();
    const[self_contribution_limit_HSA,setSelf_Contribution_Limit_HSA]=useState<number>();
    const[self_contribution_limit_FSA,setSelf_Contribution_Limit_FSA]=useState<number>();
    const[self_contribution_limit_ROTHIRA,setSelf_Contribution_Limit_ROTHIRA]=useState<number>();
    const [errorMessage, setErrorMessage] = useState('');
    async function createPlanByUser(){
        const createPlanByUserPayLoad = { id:userId,dob,salary,self_contribution_limit_401K,self_contribution_limit_HSA,self_contribution_limit_FSA, self_contribution_limit_ROTHIRA} ;
        console.log(createPlanByUserPayLoad);
        if(!salary || !dob || !self_contribution_limit_401K || !self_contribution_limit_HSA || !self_contribution_limit_FSA || !self_contribution_limit_ROTHIRA) {
          setErrorMessage("Please fill out all the fields");
        }
        else{
        await createUserPlan(createPlanByUserPayLoad).then((response)=>{
          console.log(response);
          response.data?.errorMessage? setErrorMessage(response.data.errorMessage) 
          : navigate(`/getUserPlanById/${userId}`);
        });
      }
    };

    return(
      <Container maxWidth="xs">
      <Box sx={{ mt: 10, display: "flex", flexDirection: "column", alignItems: "center" }}>
        <Avatar sx={{ m: 1, bgcolor: "primary.light" }}>
          <EditIcon />
        </Avatar>
        <Typography variant="h5">Create Retirement Investments Recurring Plan</Typography>
        <Grid container spacing={2}>
        <Grid item xs={12}>
        <TextField value={userId} name='id' label="Employee ID" sx={{ m: 1 , fontWeight:'bold'}} id="id"  margin="normal" fullWidth autoFocus disabled/>
        </Grid>
        <Grid item xs={6} md={6}>
          <TextField name='dob' label="Enter Date of Birth" placeholder="MM/DD/YYYY" sx={{ m: 1 }} id="dob"  margin="normal" fullWidth autoFocus onChange={(s) => {
            const selectedDate = s.target.value ? new Date(s.target.value) : null;
            const formattedDate = selectedDate ? selectedDate.toLocaleDateString('en-US') : null;
            setDob(formattedDate);}}/>
        </Grid>
        <Grid item xs={6} md={6}>
        <TextField value={salary} name='salary' type="number" label="Enter Salary" placeholder="$" id="salary" sx={{ m: 1 }}  margin="normal" fullWidth autoFocus onChange={(s)=> setSalary(Number(s.target.value))}/>
        </Grid>
        <Grid item xs={6} >
        <TextField value={self_contribution_limit_401K} type="number" inputProps={{min:0, max:6}} name='self_contribution_limit_401K' label="Enter Self 401k Contribution" sx={{ m: 1 , width: "100%" }} id="self_contribution_limit_401K" margin="normal" fullWidth autoFocus onChange={(s) => setSelf_Contribution_Limit_401K(Number(s.target.value))} />
        </Grid>
        <Grid item xs={6}>
        <FormHelperText  sx={{ m: 1, fontWeight: 'bold',color: "secondary.main", width: "100%" }} >Employee can only contribute maximum of 6% of their salary to 401k Account </FormHelperText>
        </Grid>
        <Grid item xs={6}>
        <TextField value={self_contribution_limit_HSA} type="number" inputProps={{min:0, max:5150}} name='self_contribution_amount_HSA' label="Enter Self HSA Contribution" sx={{ m: 1 }} id="self_contribution_amount_401K" margin="normal" fullWidth autoFocus onChange={(s) => setSelf_Contribution_Limit_HSA(Number(s.target.value))} />
        </Grid>
        <Grid item xs={6}>
        <FormHelperText  sx={{ m: 1, fontWeight: 'bold',color: "secondary.main" }} >Employee can only contribute maximum of $5150 if age is more than 55 or else $4150 to HSA Account</FormHelperText>
        </Grid>
        <Grid item xs={6}>
        <TextField value={self_contribution_limit_FSA} type="number" name='self_contribution_amount_FSA' inputProps={{min:0, max:3200}} label="Enter Self FSA Contribution" sx={{ m: 1 }} id="self_contribution_amount_401K" margin="normal" fullWidth autoFocus onChange={(s) => setSelf_Contribution_Limit_FSA(Number(s.target.value))} />
        </Grid>
        <Grid item xs={6}>
        <FormHelperText  sx={{ m: 1, fontWeight: 'bold',color: "secondary.main"}} >Employee can only contribute maximum of $3200 to FSA Account</FormHelperText>
        </Grid>
        <Grid item xs={6}>
        <TextField value={self_contribution_limit_ROTHIRA} type="number" name='self_contribution_amount_ROTHIRA' inputProps={{min:0, max:8000}} label="Enter Self ROTH IRA Contribution" sx={{ m: 1 }} id="self_contribution_amount_401K" margin="normal" fullWidth autoFocus onChange={(s) => setSelf_Contribution_Limit_ROTHIRA(Number(s.target.value))} />
        </Grid>
        <Grid item xs={6}>
        <FormHelperText  sx={{ m: 1, fontWeight: 'bold',color: "secondary.main"}} >Employee can only contribute maximum of $8000 if age is more than 50yrs or else $7000 to ROTH IRA Account</FormHelperText>
        </Grid>
        </Grid>
        {errorMessage && (
                            <Typography variant="body2" color="error" sx={{ mt: 1 }}>
                                {errorMessage}
                            </Typography>
            )}
        <Button sx={{ mt:0.8, marginLeft: "12px",backgroundColor: "#FFF" }} variant="contained"  onClick={(createPlanByUser)}>
         <NavLink to={``}>Save</NavLink>
        </Button>
        <Button sx={{ mt:0.8, marginLeft: "12px",backgroundColor: "#FFF" }} variant="contained">
          <NavLink to={`/getUserPlanById/${userId}`}>Cancel</NavLink>
        </Button>
      </Box>
    </Container>
    )
}

export default CreateRecurringPlanByUserComponent;