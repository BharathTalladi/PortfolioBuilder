import { useState } from "react";
import { Container,Box, Typography,Button,TextField,Avatar,Grid,FormHelperText} from "@mui/material";
import EditIcon from '@mui/icons-material/Edit';
import { NavLink,useNavigate,useLocation } from "react-router-dom";
import { createUserPlanByEmployer } from "../Service/Service";


const CreateUserRecurringPlanByEmployerComponent = ()=>{
    const location = useLocation();
    const navigate=useNavigate();
    console.log(location?.state?.userPlanEmployerData)
    const[id]=useState(location?.state?.userPlanEmployerData?.employee_id);
    const[salary]=useState<number>(location?.state?.userPlanEmployerData?.employee_salary);
    const[employee_contribution_limit_401K]=useState<number>(location?.state?.userPlanEmployerData?.self_contribution_limit_401K);
    const[employee_contribution_limit_HSA]=useState<number>(location?.state?.userPlanEmployerData?.self_contribution_limit_HSA);
    const[employee_contribution_limit_FSA]=useState<number>(location?.state?.userPlanEmployerData?.self_contribution_limit_FSA);
    const[employee_contribution_limit_ROTHIRA]=useState<number>(location?.state?.userPlanEmployerData?.self_contribution_limit_ROTHIRA);
    const[employer_contribution_limit_401k,setEmployer_Contribution_Limit_401k]=useState<number>();
    const[employer_contribution_limit_HSA,setEmployer_Contribution_Limit_HSA]=useState<number>();
    const[employer_contribution_limit_FSA,setEmployer_Contribution_Limit_FSA]=useState<number>();
    const[employer_contribution_limit_ROTHIRA,setEmployer_Contribution_Limit_ROTHIRA]=useState<number>();
  

    async function addEmployerContributions(){
        const addEmployerContributionsPayLoad={id,employer_contribution_limit_401k,employer_contribution_limit_HSA,employer_contribution_limit_FSA,employer_contribution_limit_ROTHIRA};
        console.log(addEmployerContributionsPayLoad);
        await createUserPlanByEmployer(addEmployerContributionsPayLoad).then((response)=>{
            console.log(response);
            navigate(`/getAllUsersPlan`);
        })
    }


    return(
        <Container maxWidth="xs">
        <Box sx={{mt:10,display: "flex",flexDirection: "column",alignItems: "center",}}>
        <Avatar sx={{ m: 1, bgcolor: "primary.light" }}>
        <EditIcon />
        </Avatar>
        <Typography variant="h5">Add Employer Contribution Details</Typography>
        <Grid container spacing={2}>
            <Grid item xs={12}>
                <TextField value={id} name='id' label="Employee ID" sx={{ m: 1 , fontWeight:'bold'}} id="id"  margin="normal" fullWidth autoFocus disabled/>
                <TextField value={salary} name='id' label="Employee Salary" sx={{ m: 1 , fontWeight:'bold'}} id="salary"  margin="normal" fullWidth autoFocus disabled/>
            </Grid>
            <Grid item xs={4}>
            <TextField value={employer_contribution_limit_401k} name='employer_contribution_amount_401K' label="Add Employer 401k Contribution" 
            sx={{ m: 1 }} id="employer_contribution_amount_401K" margin="normal" fullWidth autoFocus onChange={(s)=> setEmployer_Contribution_Limit_401k(Number(s.target.value))}/>
            </Grid>
            <Grid item xs={4}>
            <FormHelperText  sx={{ m: 1, fontWeight: 'bold',color: "secondary.main", width: "100%" }} >Employer should match 100% to employee 401k Account</FormHelperText>
            </Grid>
            <Grid item xs={4}>
            <TextField value={employee_contribution_limit_401K} name='employee_contribution_amount_401K' label="Employee 401k Contribution" 
            sx={{ m: 1 }} id="employee_contribution_amount_401K" margin="normal" fullWidth autoFocus disabled/>
            </Grid>
            <Grid item xs={4}>
            <TextField value={employer_contribution_limit_HSA} name='employer_contribution_amount_HSA' label="Add Employer HSA Contribution" 
                      sx={{ m: 1 }} id="employer_contribution_amount_HSA"  margin="normal" fullWidth autoFocus onChange={(s)=> setEmployer_Contribution_Limit_HSA(Number(s.target.value))}/>
            </Grid>
            <Grid item xs={4}>
            <FormHelperText  sx={{ m: 1, fontWeight: 'bold',color: "secondary.main", width: "100%" }} >Employer can only contribute maximum of $2100 to employee HSA Account</FormHelperText>
            </Grid>
            <Grid item xs={4}>
            <TextField value={employee_contribution_limit_HSA} name='employee_contribution_amount_HSA' label="Employee HSA Contribution" 
            sx={{ m: 1 }} id="employee_contribution_amount_HSA" margin="normal" fullWidth autoFocus disabled/>
            </Grid>
            <Grid item xs={4}>
            <TextField value={employer_contribution_limit_FSA} name='employer_contribution_amount_FSA' label="Add Employer FSA Contribution" 
                      sx={{ m: 1}} id="employer_contribution_amount_FSA" margin="normal" fullWidth autoFocus onChange={(s)=> setEmployer_Contribution_Limit_FSA(Number(s.target.value))}/>
            </Grid>
            <Grid item xs={4}>
            <FormHelperText  sx={{ m: 1, fontWeight: 'bold',color: "secondary.main", width: "100%" }} >Employer can only contribute maximum of $3200 to employee FSA Account</FormHelperText>
            </Grid>
            <Grid item xs={4}>
            <TextField value={employee_contribution_limit_FSA} name='employee_contribution_amount_FSA' label="Employee FSA Contribution" 
            sx={{ m: 1 }} id="employee_contribution_amount_FSA" margin="normal" fullWidth autoFocus disabled/>
            </Grid>
            <Grid item xs={4}>
            <TextField value={employer_contribution_limit_ROTHIRA} name='employer_contribution_amount_ROTHIRA' label="Add Employer ROTH IRA Contribution" 
                      sx={{ m: 1 }} id="employer_contribution_amount_ROTHIRA" margin="normal" fullWidth autoFocus onChange={(s)=> setEmployer_Contribution_Limit_ROTHIRA(Number(s.target.value))}/> 
            </Grid>
            <Grid item xs={4}>
            <FormHelperText  sx={{ m: 1, fontWeight: 'bold',color: "secondary.main", width: "100%" }} >Employee can only contribute $ to employee ROTH IRA Account</FormHelperText>
            </Grid>
            <Grid item xs={4}>
            <TextField value={employee_contribution_limit_ROTHIRA} name='employee_contribution_amount_ROTHIRA' label="Employee ROTHIRA Contribution" 
            sx={{ m: 1 }} id="employee_contribution_amount_ROTHIRA" margin="normal" fullWidth autoFocus disabled/>
            </Grid>
            <Grid item xs={12}>
                <Button sx={{ mt:0.8,marginLeft: "auto",backgroundColor: "#FFF" }} variant="contained" onClick={addEmployerContributions}><NavLink to={""}>Save</NavLink></Button>
                <Button sx={{ mt:0.8, marginLeft: "12px",backgroundColor: "#FFF" }} variant="contained"><NavLink to={`/getAllUsersPlan`}>Cancel</NavLink></Button>
            </Grid>
        </Grid>
        </Box>
        </Container>
    );
};
export default CreateUserRecurringPlanByEmployerComponent;