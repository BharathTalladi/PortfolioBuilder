import { useState } from "react";
import { useParams, useLocation, NavLink, useNavigate} from "react-router-dom";
import { editUserContributions } from "../Service/Service";
import { Container,Box, Typography,Button,TextField,Avatar} from "@mui/material";
import EditIcon from '@mui/icons-material/Edit';

const EditUserContributionsComponent=()=>{

    const {id} = useParams<{id?:string}>();
    const location = useLocation();
    const [salary,setSalary]=useState<number>(location?.state?.userPlanData?.salary);
    const[self_contribution_limit_401K,setSelf_Contribution_Limit_401K]=useState<number>(location?.state?.userPlanData?.self_contribution_limit_401K);
    const[self_contribution_limit_HSA,setSelf_Contribution_Limit_HSA]=useState<number>(location?.state?.userPlanData?.self_contribution_limit_HSA);
    const[self_contribution_limit_FSA,setSelf_Contribution_Limit_FSA]=useState<number>(location?.state?.userPlanData?.self_contribution_limit_FSA);
    const[self_contribution_limit_ROTHIRA,setSelf_Contribution_Limit_ROTHIRA]=useState<number>(location?.state?.userPlanData?.self_contribution_limit_ROTHIRA);
    const navigate=useNavigate();

    const [salaryError, setSalaryError] = useState(false);
    const [selfContributionLimit401KError, setSelfContributionLimit401KError] = useState(false);
    const [selfContributionLimitHSAError, setSelfContributionLimitHSAError] = useState(false);
    const [selfContributionLimitFSAError, setSelfContributionLimitFSAError] = useState(false);
    const [selfContributionLimitROTHIRAError, setSelfContributionLimitROTHIRAError] = useState(false);
      
    async function updateUserContributions(){
        if(!salary && !self_contribution_limit_401K && !self_contribution_limit_HSA && !self_contribution_limit_FSA && !self_contribution_limit_ROTHIRA){
        setSalaryError(true);
        setSelfContributionLimit401KError(true);
        setSelfContributionLimitHSAError(true);
        setSelfContributionLimitFSAError(true);
        setSelfContributionLimitROTHIRAError(true);
        return;
        }
        setSalaryError(false);
        setSelfContributionLimit401KError(false);
        setSelfContributionLimitHSAError(false);
        setSelfContributionLimitFSAError(false);
        setSelfContributionLimitROTHIRAError(false);
        const editUserContributionDetails={salary,self_contribution_limit_401K,self_contribution_limit_HSA,self_contribution_limit_FSA,self_contribution_limit_ROTHIRA};
        console.log(editUserContributionDetails);
        await editUserContributions(id,editUserContributionDetails).then((response)=>{
            navigate(`/getUserPlanById/${id}`, {
              state: {
                ...location.state, // Keep the existing state
                userPlanData: { // Update the userPlanData with the edited values
                  ...location.state.userPlanData,
                  salary: salary,
                  self_contribution_limit_401K: self_contribution_limit_401K,
                  self_contribution_limit_HSA: self_contribution_limit_HSA,
                  self_contribution_limit_FSA: self_contribution_limit_FSA,
                  self_contribution_limit_ROTHIRA: self_contribution_limit_ROTHIRA,
                },
              },
            });
            
        }).catch(error => {
          console.error(error);
        })
    }

    return(
        <Container maxWidth="xs">
        <Box sx={{mt:10,display: "flex",flexDirection: "column",alignItems: "center",}}>
        <Avatar sx={{ m: 1, bgcolor: "primary.light" }}>
        <EditIcon />
        </Avatar>
        <Typography variant="h5">Update Self Contribution Details</Typography>
            <Box>
                    <TextField value={salary} name='salary' label="Edit Salary" sx={{ m: 1 }} id="salary"  margin="normal" error={salaryError}
                    fullWidth autoFocus onChange={(s)=> {setSalary(Number(s.target.value)), setSalaryError(!s.target.value)}} required/>
                    {salaryError && <Typography variant="body2" color="error">Salary is required.</Typography>}
                    <TextField value={self_contribution_limit_401K} name='self_contribution_amount_401K' label="Edit Self 401k Contribution"  error={selfContributionLimit401KError}
                      sx={{ m: 1 }} id="self_contribution_amount_401K"margin="normal" fullWidth autoFocus onChange={(s)=> (setSelf_Contribution_Limit_401K(Number(s.target.value)),setSelfContributionLimit401KError(!s.target.value))} required/>
                    {selfContributionLimit401KError && <Typography variant="body2" color="error">Self 401k Contribution is required.</Typography>}
                    <TextField value={self_contribution_limit_HSA} name='self_contribution_amount_HSA' label="Edit Self HSA Contribution"  error={selfContributionLimitHSAError}
                      sx={{ m: 1 }} id="self_contribution_amount_HSA"  margin="normal" fullWidth autoFocus onChange={(s)=> {setSelf_Contribution_Limit_HSA(Number(s.target.value)),setSelfContributionLimitHSAError(!s.target.value)}} required/>
                    {selfContributionLimitHSAError && <Typography variant="body2" color="error">Self HSA Contribution is required.</Typography>}
                    <TextField value={self_contribution_limit_FSA} name='self_contribution_amount_FSA' label="Edit Self FSA Contribution" error={selfContributionLimitFSAError}
                      sx={{ m: 1}} id="self_contribution_amount_FSA" margin="normal" fullWidth autoFocus onChange={(s)=> {setSelf_Contribution_Limit_FSA(Number(s.target.value)),setSelfContributionLimitFSAError(!s.target.value)}} required/>
                    {selfContributionLimitFSAError && <Typography variant="body2" color="error">Self FSA Contribution is required.</Typography>}
                    <TextField value={self_contribution_limit_ROTHIRA} name='self_contribution_amount_ROTHIRA' label="Edit Self ROTH IRA Contribution" error={selfContributionLimitROTHIRAError}
                      sx={{ m: 1 }} id="self_contribution_amount_ROTHIRA" margin="normal" fullWidth autoFocus onChange={(s)=> {setSelf_Contribution_Limit_ROTHIRA(Number(s.target.value)),setSelfContributionLimitROTHIRAError(!s.target.value)}} required/> 
                    {selfContributionLimitROTHIRAError && <Typography variant="body2" color="error">Self ROTHIRA Contribution is required.</Typography>}
                    <Button sx={{ mt:0.8,marginLeft: "auto",backgroundColor: "#FFF" }} variant="contained" onClick={(updateUserContributions)}>
                      <NavLink to={`/getUserPlanById/${id}`}>Save</NavLink>
                    </Button>
                    <Button sx={{ mt:0.8, marginLeft: "12px",backgroundColor: "#FFF" }} variant="contained"><NavLink to={`/getUserPlanById/${id}`}>Cancel</NavLink></Button>
            </Box>
        </Box>
        </Container>
    )
}

export default EditUserContributionsComponent;