SELECT agent.name as "NAME", count(mutant.*) as "SCORE" FROM agent
INNER JOIN mutant on mutant.recruiterId=agent.agentId
group by agent.name
order by "SCORE" desc
limit 10;