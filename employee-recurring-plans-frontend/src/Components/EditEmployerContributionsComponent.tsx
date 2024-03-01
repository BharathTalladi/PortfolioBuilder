import { useState, useEffect } from "react";
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
import { NavLink, useLocation,useParams } from "react-router-dom";
import { getUserPlanById } from "../Service/Service";

const EditEmployerContributionsComponent = () => {
  //const location = useLocation();
  //const userPlan = location.state?.userPlan; // Access user plan data from props
  const [
    employer_contribution_limit_401k,
    setEmployer_Contribution_Limit_401k,
  ] = useState("");
  const [employer_contribution_limit_HSA, setEmployer_Contribution_Limit_HSA] =
    useState("");
  const [employer_contribution_limit_FSA, setEmployer_Contribution_Limit_FSA] =
    useState("");
  const [
    employer_contribution_limit_ROTHIRA,
    setEmployer_Contribution_Limit_ROTHIRA,
  ] = useState("");

  const [userPlan, setUserPlan] = useState<UserPlan | null>(null);
  const { id } = useParams<{ id: string }>();

  useEffect(() => {
    const fetchUserPlan = async () => {
      try {
        const response = await getUserPlanById(id); // Fetch the user plan based on ID
        setUserPlan(response.data);
      } catch (error) {
        console.error("Error fetching user plan:", error);
        // Handle error gracefully, e.g., display an error message to the user
      }
    };

    fetchUserPlan();
  }, [id]);

  return (
    <Container maxWidth="xs">
      <Box
        sx={{
          mt: 10,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <Grid container spacing={2}>
          <Grid item xs={6}>
            <Box>
              <Avatar sx={{ m: 1, bgcolor: "primary.light" }}>
                <EditIcon />
              </Avatar>
              <Typography variant="h5">
                Update Employer Contribution Details
              </Typography>
              <TextField
                value={employer_contribution_limit_401k}
                name="employer_contribution_limit_401k"
                label="Edit Employer 401k Contribution"
                sx={{ m: 1 }}
                id="employer_contribution_limit_401k"
                margin="normal"
                fullWidth
                autoFocus
                onChange={(s) =>
                  setEmployer_Contribution_Limit_401k(s.target.value)
                }
              />
              <TextField
                value={employer_contribution_limit_HSA}
                name="employer_contribution_limit_HSA"
                label="Edit Employer HSA Contribution"
                sx={{ m: 1 }}
                id="employer_contribution_limit_HSA"
                margin="normal"
                fullWidth
                autoFocus
                onChange={(s) =>
                  setEmployer_Contribution_Limit_HSA(s.target.value)
                }
              />
              <TextField
                value={employer_contribution_limit_FSA}
                name="employer_contribution_limit_FSA"
                label="Edit Employer FSA Contribution"
                sx={{ m: 1 }}
                id="employer_contribution_limit_FSA"
                margin="normal"
                fullWidth
                autoFocus
                onChange={(s) =>
                  setEmployer_Contribution_Limit_FSA(s.target.value)
                }
              />
              <TextField
                value={employer_contribution_limit_ROTHIRA}
                name="employer_contribution_limit_ROTHIRA"
                label="Edit Employer ROTH IRA Contribution"
                sx={{ m: 1 }}
                id="employer_contribution_limit_ROTHIRA"
                margin="normal"
                fullWidth
                autoFocus
                onChange={(s) =>
                  setEmployer_Contribution_Limit_ROTHIRA(s.target.value)
                }
              />
            </Box>
          </Grid>
          <Grid item xs={6}>
            <Box>
              <Typography variant="h5">Self Contribution Details</Typography>
              {userPlan && (
                <div>
                  <TextField
                    value={
                      userPlan.selfContributionAmount
                        .self_contribution_limit_401k
                    }
                    name="self_contribution_limit_401k"
                    label="Self 401k Contribution"
                    sx={{ m: 1 }}
                    id="employer_contribution_limit_401k"
                    margin="normal"
                    fullWidth
                    autoFocus
                  />
                  <TextField
                    value={
                      userPlan.selfContributionAmount
                        .self_contribution_limit_HSA
                    }
                    name="self_contribution_limit_HSA"
                    label="Self HSA Contribution"
                    sx={{ m: 1 }}
                    id="self_contribution_limit_HSA"
                    margin="normal"
                    fullWidth
                    autoFocus
                  />
                  <TextField
                    value={
                      userPlan.selfContributionAmount
                        .self_contribution_limit_FSA
                    }
                    name="self_contribution_limit_FSA"
                    label="Self FSA Contribution"
                    sx={{ m: 1 }}
                    id="Self_contribution_limit_FSA"
                    margin="normal"
                    fullWidth
                    autoFocus
                  />
                  <TextField
                    value={
                      userPlan.selfContributionAmount
                        .self_contribution_limit_ROTHIRA
                    }
                    name="self_contribution_limit_ROTHIRA"
                    label="Self ROTH IRA Contribution"
                    sx={{ m: 1 }}
                    id="self_contribution_limit_ROTHIRA"
                    margin="normal"
                    fullWidth
                    autoFocus
                  />
                </div>
              )}
            </Box>
          </Grid>
        </Grid>
        <Button
          sx={{ mt: 0.8, marginLeft: "auto", backgroundColor: "#FFF" }}
          variant="contained"
        >
          <NavLink to={`/getAllUsersPlan`}>Save</NavLink>
        </Button>
        <Button
          sx={{ mt: 0.8, marginLeft: "12px", backgroundColor: "#FFF" }}
          variant="contained"
        >
          <NavLink to={`/getAllUsersPlan`}>Cancel</NavLink>
        </Button>
      </Box>
    </Container>
  );
};

export default EditEmployerContributionsComponent;
