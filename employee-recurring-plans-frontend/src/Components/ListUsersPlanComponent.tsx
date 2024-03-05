import { useEffect, useState } from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Typography,
  Button,
} from "@mui/material";
import { getAllUsersPlan } from "../Service/Service";
import { NavLink ,useNavigate} from "react-router-dom";

interface UserPlan {
  id: string;
  salary: number;
  selfContributionAmount: {
    self_contribution_amount_401K: number;
    self_contribution_amount_HSA: number;
    self_contribution_amount_FSA: number;
    self_contribution_amount_ROTHIRA: number;
  };
  employerContributionAmount: {
    employer_contribution_amount_401k: number;
    employer_contribution_amount_HSA: number;
    employer_contribution_amount_FSA: number;
    employer_contribution_amount_ROTHIRA: number;
  };
  totalContributionAmount: {
    total_contribution_amount_401k: number;
    total_contribution_amount_HSA: number;
    total_contribution_amount_FSA: number;
    total_contribution_amount_ROTHIRA: number;
  };
}

interface ContributionAmounts {
  [key: string]: number;
}

interface UserData {
  age: number;
  salary:number;
  salaryAfterContributions: number;
  selfContributionAmount: ContributionAmounts;
  employerContributionAmount: ContributionAmounts;
  totalContributionAmount: ContributionAmounts;
}

const ListUsersPlanComponent = () => {
  const [userPlans, setUserPlans] = useState<UserPlan[]>([]);
  const navigate =useNavigate();
  // Fetch user plans on component mount
  useEffect(() => {
    const fetchUserPlans = async () => {
      try {
        const response = await getAllUsersPlan();
        setUserPlans(response.data);
      } catch (error) {
        console.error("Error fetching user plans:", error);
      }
    };

    fetchUserPlans();
  }, []);


  return (
    <TableContainer component={Paper}>
      <Typography variant="h6" sx={{ mb: 2 }}>
        Users' Plans
      </Typography>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Employee ID</TableCell>
            <TableCell>Salary</TableCell>
            <TableCell>Self Contributions</TableCell>
            <TableCell>Employer Contributions</TableCell>
            <TableCell>Total Retirement Contributions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {userPlans.map((userPlan) => (
            <TableRow key={userPlan.id}>
              <TableCell>{userPlan.id}</TableCell>
              <TableCell>
                ${userPlan.salary}
              </TableCell>
              <TableCell>
                <ul>
                  <li>401(k): $ {userPlan.selfContributionAmount.self_contribution_amount_401K}</li>
                  <li>HSA: ${userPlan.selfContributionAmount.self_contribution_amount_HSA}</li>
                  <li>FSA: ${userPlan.selfContributionAmount.self_contribution_amount_FSA}</li>
                  <li>ROTHIRA: ${userPlan.selfContributionAmount.self_contribution_amount_ROTHIRA}</li>
                </ul>
              </TableCell>

              {
                userPlan?.employerContributionAmount?.employer_contribution_amount_401k ? (
                    <>
                    <TableCell>
                <ul>
                  <li>401(k): ${userPlan.employerContributionAmount.employer_contribution_amount_401k}</li>
                  <li>HSA: ${userPlan.employerContributionAmount.employer_contribution_amount_HSA}</li>
                  <li>FSA: ${userPlan.employerContributionAmount.employer_contribution_amount_FSA}</li>
                  <li>ROTHIRA: ${userPlan.employerContributionAmount.employer_contribution_amount_ROTHIRA}</li>
                  <Button  fullWidth variant="contained" sx={{ mt: 3, mb: 2 }} onClick={()=> navigate(`/editEmployerContributions/${userPlan.id}`, {
                                            state: {
                                                userPlanEmployerData: {
                                                  employee_salary:userPlan.salary,
                                                  self_contribution_limit_401K:userPlan.selfContributionAmount.self_contribution_amount_401K,
                                                  self_contribution_limit_HSA:userPlan.selfContributionAmount.self_contribution_amount_HSA,
                                                  self_contribution_limit_FSA:userPlan.selfContributionAmount.self_contribution_amount_FSA,
                                                  self_contribution_limit_ROTHIRA:userPlan.selfContributionAmount.self_contribution_amount_ROTHIRA,
                                                  employer_contribution_limit_401k: userPlan.employerContributionAmount.employer_contribution_amount_401k,
                                                  employer_contribution_limit_HSA: userPlan.employerContributionAmount.employer_contribution_amount_HSA,
                                                  employer_contribution_limit_FSA: userPlan.employerContributionAmount.employer_contribution_amount_FSA,
                                                  employer_contribution_limit_ROTHIRA: userPlan.employerContributionAmount.employer_contribution_amount_ROTHIRA,
                                                },
                                            },
                    })}>
                    Edit Employer Contributions 
                  </Button>
                </ul>
              </TableCell>
              <TableCell>
                <ul>
                  <li>401(k): ${userPlan.totalContributionAmount.total_contribution_amount_401k}</li>
                  <li>HSA: ${userPlan.totalContributionAmount.total_contribution_amount_HSA}</li>
                  <li>FSA: ${userPlan.totalContributionAmount.total_contribution_amount_FSA} </li>
                  <li> ROTHIRA: ${userPlan.totalContributionAmount.total_contribution_amount_ROTHIRA}</li>
                </ul>
                </TableCell>
                    </>
                ):(
                  <>
                  <TableCell>
                    <Button  fullWidth variant="contained" sx={{ mt: 2, mb: 1 }} >
                    <NavLink to={`/createUserPlanByEmployer?id=${userPlan.id}`} />
                    Add Employer Contributions
                    </Button>
                  </TableCell>
                  <TableCell>
                    After Employer COntributes Total Contributions will be calculated
                  </TableCell>
                  </>
                )
              }
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default ListUsersPlanComponent;
