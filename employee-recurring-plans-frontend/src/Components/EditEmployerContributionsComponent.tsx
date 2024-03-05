import { useState } from "react";
import {
  Container,
  Box,
  Typography,
  Button,
  TextField,
  Avatar,
  Grid,
} from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import { NavLink, useLocation,useNavigate,useParams } from "react-router-dom";
import { editEmployerContributions } from "../Service/Service";

const EditEmployerContributionsComponent = () => {
  const location = useLocation();
  const {id} = useParams<{id?:string}>();
  const navigate=useNavigate();
  const [employer_contribution_limit_401k,setEmployer_Contribution_Limit_401k,] = useState<number>(location?.state?.userPlanEmployerData?.employer_contribution_limit_401k);
  const [employer_contribution_limit_HSA,setEmployer_Contribution_Limit_HSA] =useState<number>(location?.state?.userPlanEmployerData?.employer_contribution_limit_HSA);
  const [employer_contribution_limit_FSA,setEmployer_Contribution_Limit_FSA] =useState<number>(location?.state?.userPlanEmployerData?.employer_contribution_limit_FSA);
  const [employer_contribution_limit_ROTHIRA,setEmployer_Contribution_Limit_ROTHIRA,] = useState<number>(location?.state?.userPlanEmployerData?.employer_contribution_limit_ROTHIRA);
  const [employee_contribution_limit_401k]=useState<number>(location?.state?.userPlanEmployerData?.self_contribution_limit_401K);
  const [employee_contribution_limit_HSA]=useState<number>(location?.state?.userPlanEmployerData?.self_contribution_limit_FSA);
  const [employee_contribution_limit_FSA]=useState<number>(location?.state?.userPlanEmployerData?.self_contribution_limit_HSA);
  const [employee_contribution_limit_ROTHIRA]=useState<number>(location?.state?.userPlanEmployerData?.self_contribution_limit_ROTHIRA);

  async function updateEmployerContributions(){
    const editEmployerContributionDetails={employer_contribution_limit_401k,employer_contribution_limit_HSA,employer_contribution_limit_FSA,employer_contribution_limit_ROTHIRA};
        console.log(editEmployerContributionDetails);

    await editEmployerContributions(id,editEmployerContributionDetails).then((response)=>{
      navigate(`/getAllUsersPlan`, {
        state: {
          ...location.state, // Keep the existing state
          userPlanData: { // Update the userPlanData with the edited values
            ...location.state.userPlanEmployerData,
            employer_contribution_limit_401k: employer_contribution_limit_401k,
            employer_contribution_limit_HSA: employer_contribution_limit_HSA,
            employer_contribution_limit_FSA: employer_contribution_limit_FSA,
            employer_contribution_limit_ROTHIRA: employer_contribution_limit_ROTHIRA,
          },
        },
      });
    }).catch(error => {
    console.error(error);
  })
  }


  return (
    <Container maxWidth="xs">
      <Box  sx={{ mt:10,display: "flex",  flexDirection: "column",  alignItems: "center"  }}>
        <Grid container spacing={2}>
          <Grid item xs={6}>
            <Box>
              <Avatar sx={{ m: 1,marginLeft:10, bgcolor: "primary.light" }}>
                <EditIcon />
              </Avatar>
              <Typography variant="h5">
                Update Employer Contribution Details
              </Typography>
              <TextField
                value={employer_contribution_limit_401k}  name="employer_contribution_limit_401k"  label="Edit Employer 401k Contribution"  sx={{ m: 1 }}  
                id="employer_contribution_limit_401k"  margin="normal"  fullWidth  autoFocus  onChange={(s) =>  setEmployer_Contribution_Limit_401k(Number(s.target.value))}/>
              <TextField  value={employer_contribution_limit_HSA}  name="employer_contribution_limit_HSA"  label="Edit Employer HSA Contribution"  sx={{ m: 1 }}
                id="employer_contribution_limit_HSA"  margin="normal"  fullWidth  autoFocus  onChange={(s) =>  setEmployer_Contribution_Limit_HSA(Number(s.target.value))}/>
              <TextField  value={employer_contribution_limit_FSA}  name="employer_contribution_limit_FSA"  label="Edit Employer FSA Contribution"  sx={{ m: 1 }}
                id="employer_contribution_limit_FSA"  margin="normal"  fullWidth  autoFocus  onChange={(s) =>  setEmployer_Contribution_Limit_FSA(Number(s.target.value))  } />
              <TextField  value={employer_contribution_limit_ROTHIRA}  name="employer_contribution_limit_ROTHIRA"  label="Edit Employer ROTH IRA Contribution"  sx={{ m: 1 }} 
                id="employer_contribution_limit_ROTHIRA"  margin="normal"  fullWidth  autoFocus  onChange={(s) =>setEmployer_Contribution_Limit_ROTHIRA(Number(s.target.value))}/>
            </Box>
          </Grid>
          <Grid item xs={6}>
            <Box  sx={{ mt:6,display: "flex",  flexDirection: "column",  alignItems: "center"  }}>
              <Typography variant="h5">Employee Contribution Details</Typography>
                  <TextField value={employee_contribution_limit_401k} name="employee_contribution_limit_401k"  label="Employee 401k Contribution" sx={{ m: 1 }}
                    id="employee_contribution_limit_401k"  margin="normal" fullWidth  autoFocus disabled/>
                  <TextField value={employee_contribution_limit_HSA}  name="employee_contribution_limit_HSA" label="Employee HSA Contribution"  sx={{ m: 1 }}
                    id="employee_contribution_limit_HSA" margin="normal"  fullWidth  autoFocus disabled />
                  <TextField value={employee_contribution_limit_FSA}name="employee_contribution_limit_FSA" label="Employee FSA Contribution"  sx={{ m: 1 }}
                    id="employee_contribution_limit_FSA"  margin="normal"  fullWidth  autoFocus disabled />
                  <TextField  value={employee_contribution_limit_ROTHIRA} name="employee_contribution_limit_ROTHIRA"  label="Employee ROTH IRA Contribution"  sx={{ m: 1 }}
                    id="employee_contribution_limit_ROTHIRA"  margin="normal"  fullWidth  autoFocus disabled />
            </Box>
          </Grid>
        </Grid>
        <Button sx={{ mt: 0.8, marginLeft: "auto", backgroundColor: "#FFF" }} variant="contained" onClick={(updateEmployerContributions)} >
          <NavLink to={`/getAllUsersPlan`}>Save</NavLink>
        </Button>
        <Button sx={{ mt: 0.8, marginLeft: "12px", backgroundColor: "#FFF" }} variant="contained">
          <NavLink to={`/getAllUsersPlan`}>Cancel</NavLink>
        </Button>
      </Box>
    </Container>
  );
};

export default EditEmployerContributionsComponent;
