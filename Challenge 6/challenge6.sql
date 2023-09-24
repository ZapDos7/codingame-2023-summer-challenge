
-- 1 student group size = 7 at night of may 12th 3961

-- 2 group chat creation date at most <=  may 5th 3961  
WITH StudentIdsPerChatCreation AS (
    select oc.chatid
    from onlineChat oc
    where oc.createdAt >= dateadd(week,-1 ,'3961-05-12')
    and createdAt <'3961-05-12'
), 
-- 3 all 7 students grade > median grade
-- (assume this logic for median computation, TLDR: order resultset by grade and then find grade in the middle)
GoodStudents AS (
select StudentId
from student s
where s.AVGGRADE>
(select avg(AVGGRADE) from student s)
),
ChemistryCulprit AS (
-- 4 strong acid , chemistry supply room access, the one who stayed the longest
select st.StudentId from roomAccessHistory access, student st
where access.studentId = st.studentId
and access.enteredAt >= '3961-05-12'
and access.enteredAt <= dateadd(day, 1 ,'3961-05-12')
and access.roomid = (select roomid from room where roomName = 'Chemistry supply room')
order by datediff(day, access.exitedAt, access.enteredAt) desc
limit 1
),
-- 5 some students were not in schedule after 15:00 May 12th 3961 , at least 3 needed
AvailableStudents AS (
select distinct studentid from student st , class c , schedule s 
where st.classid = c.classid and s.classid = c.classid
and s.day = '3961-05-12' and s.hour = 15
),
-- 6 two of them were very tall
TallStudents AS (
select studentid from student order by height desc
),
-- 7 one has a M or W in the name
NameMatches AS (
select s.studentid from student s where LOWER(s.name) LIKE '%m%' AND LOWER(s.name) LIKE '%w%'
),
-- 8 out of the 7, 3 were living in the same room
Roommates AS (
select st.studentid from student st , room r 
where st.bedroomid = r.roomid 
and r.roomid = (select bedroomid from student where studentid in (select * from ChemistryCulprit))
)

SELECT DISTINCT s.name
FROM student s
JOIN GoodStudents gs ON gs.studentId = s.studentId
JOIN ChemistryCulprit cc ON gs.studentId = cc.studentId 
JOIN TallStudents ts ON s.studentId = ts.studentId
JOIN NameMatches nm ON s.studentId = nm.studentId
JOIN Roommates r ON s.bedroomId = r.roomId
-- JOIN StudentIdsPerChatCreation chat ON / gs.chatId = chat.chatId
;

-- All together:
-- student @thiefId from (4) is for sure found.
-- his room is too so (B) gives another 2 students , 3 in total

-- for the chat we try to find a chat containing all 3 students
-- and keep a distinct stundentid found for those chats:
-- select distinct studentid from studentschatsmap where studenid in (select our 3 bad guys)

-- for the above resultset we:
-- we take the list from (3) and exclude from it the resultset (5)

-- we end up with slightly more studentid than 4 so in order to remove the rest we use the last two hints:
-- (B) gives us another room probably and (6) limits the results to very tall students
--
