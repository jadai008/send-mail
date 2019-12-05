# send-mail
This application contains REST service that accepts the necessary information and sends emails.

The application provides an abstraction between two different email service providers. If one of
the services goes down, the service fails over to a different provider without affecting customers.

**This application supports only plain text emails. No HTML or attachment support provided.**

## Email Gateway providers used in this project
1. [SendGrid](https://sendgrid.com/) - Primary email gateway provider
2. [Mailgun](https://www.mailgun.com/) - Secondary provider

## Building the source code
- Clone this repo to your local machine from https://github.com/jadai008/send-mail.git
- Open your favourite command line terminal (cmd, git bash etc...) 
- Change the working directory to &lt;cloned-project-location&gt;/sendmail
- Use the corresponding Maven wrapper utility to build the code (For Windows `mvnw.cmd` and for other OS use `mvnw`)
	- *nix: `./mvnw clean package -DskipTests` 
	- Windows: `mvnm.cmd clean package -DskipTests`
	
(One of the tests need a REST service running in the local machine to simulate time out. So we can skip tests while building and run it later)
		

## Running the application
There are two ways of running the code
1. Build the code using the step mentioned above and use `java -Dmailgun.apiKey=<Your MailGun Api Key> -Dsendgrid.apiKey=<Your SendGrid Api Key> -Dmailgun.domain=<Your mailgun domain name> -jar target/sendmail-0.0.1-SNAPSHOT.jar` (just after the build command succeeds)
2. Simply run `./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Dmailgun.apiKey=<Your MailGun Api Key> -Dsendgrid.apiKey=<Your SendGrid Api Key> -Dmailgun.domain=<Your mailgun domain name>"` (from &lt;cloned-project-location&gt;/sendmail directory. Use mvnw.cmd for Windows)

Alternatively you can set the following environment variables

- `MAILGUN_APIKEY=<Your MailGun Api Key>`
- `MAILGUN_DOMAIN=<Your MailGun domain>`
- `SENDGRID_APIKEY=<Your SendGrid Api key>`

and run the above commands without jvm arguments (`java -jar target/sendmail-0.0.1-SNAPSHOT.jar` or `./mvnw spring-boot:run`) 

## Usage of the REST API
For this I assume that your spring-boot application runs on `localhost` at port `8080` (default config). 

You can write simple code using [Apache HttpClient](https://hc.apache.org/httpcomponents-client-ga/index.html) to build the request and **POST** (HTTP Method) it to the URL `http://loaclhost:8080/send` or you can use [Postman](https://www.getpostman.com/) to use the API.

#### Required Header(s)
`Content-Type: application/json`

#### Required payload (body) structure

`{
	"to":["email1", "email2"....],
	"cc":["email1", "email2"....],
	"bcc":["email1", "email2"....],
	"subject":"<subject of your email>",
	"body":"<Message text>"
}`

**_cc and bcc are optional whereas the rest of the parameters are mandatory_**

## Input Validation
The following input validations are done at the backend
1. The request contains a message body
2. All the mandatory fields are present
3. The entered email addresses are in the correct format
4. The total number of recipients including to, cc and bcc should not exceed 1000.
5. The message should not be more than 25 MB in size

The user gets Http 400 status (BAD REQUEST) with the corresponding validation error message if any of the above validation fails.

## Note
[Mailinator](https://www.mailinator.com/) provides public inboxes which can be used for testing email receiving. This avoids our personal inboxes from being flooded with test emails :)

## TODO
The following tasks are not done.

1. Before sending email, the code does not check whether all the recipients are really existing. 
2. The code just returns the success status after the message is queued/dispatched to the gateway. Whether the mail is actually delivered or bounced is not being monitored.
3. The sent mails are not stored anywhere using/in the application
4. Regex used to validate email addresses is simple and may not cover corner cases.
5. No custom domain configured for MailGun. Only sandbox domain provided is used in my MailGun account. (This is also the reason why MailGun is the secondary provider. The sand box domain allows only pre-authorized/added email addresses to receive emails)
6. In the interest of time, the Json data needed for SendGrid is built using StringBuffer rather than using a class and ObjectMapper. 
