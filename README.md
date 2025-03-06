# survey-statistics

<strong>run application:</strong>
	docker-compose up -d
 </br></br>
<strong>swagger-url:</strong>
	http://localhost:8080/swagger-ui/index.html

# Technical interview task description:

You have a list with the information of 300 people who participated in different surveys.
For each survey, the respondent can receive points that can later be claimed as different gifts.

Active and inactive members are present in the file.
For a survey, only active members can be targeted, but currently inactive members might have participated in previous surveys.

We store four different participation statuses:

Not asked - Did not participate in the questionnaire (was not asked).
Rejected - Did not participate in the questionnaire (was asked, but no response).
Filtered - Based on the filtering questions, the member did not fit into the targeted group.
Completed - Filled and finished the questionnaire.
The respondent can receive points only in statuses 3 and 4 (points may differ between Filtered and Completed).

Task:
# 1. Read all the data from the following CSV files into the memory of your application:

Members.csv
Surveys.csv
Statuses.csv
Participation.csv

You can assume that the file content is not changed externally while the application is running.
Use appropriate data structures to store the data in memory.
You can use any libraries to work with the CSV files.

# 2. Implement the following functionalities:
    a. Fetch all respondents who completed the questionnaire for a given survey ID.
    b. Fetch all surveys that were completed by a given member ID.
    c. Fetch the list of points (with the related survey ID) that a member has collected so far (input is the member ID).
    d. Fetch the list of members who can be invited to a given survey (not participated in that survey yet and are active).
    e. Fetch the list of surveys with statistics, including:
        - Survey ID
        - Survey name
        - Number of completed questionnaires
        - Number of filtered participants
        - Number of rejected participants
        - Average length of time spent on the survey (Participation.length)

# 3. Create REST endpoints for the above functionalities.

Notes:
- Spring Web is recommended (other frameworks can be used if preferred).
- Documentation is not required.
- Moving the files' content into a database is not required; the application can work directly from the files (cache can be used if seen as useful).
- All functionality should be written in Java.
- Test coverage is a plus (at least some unit tests).
