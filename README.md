## Assumption and dependencies
    
	- Swagger Documentaion URL is http://localhost:8080/swagger-ui/
	- Postman collection is attached for testing
	- Retrieving general information doesn't require user authentication like subsucription plans and paymnet methods 
	- Any actions or retrieving user data require an authenticated user
	- All the registered users on the system have a userNumber that will be used For 
	     - Authentication 
	     - Service subscription management
	     - Managing cardInfo
	- User can cancel or upgrade plans after paying the current subsecription by calling calling payInvoice API
	- User van upgrade to higher plan (more duration, advanced level)
	- There are 3 levels og plans(BASIC, STANDARD, PREMIUM) each level has 2 durations (Month,Year), so the final number of plans is 6.
	- User can add max 3 cards.
	- PayInvoice service requires integration with payment gateway.
	- A payment engine is running daily to check Subscriptions due today (Its renewal date is today) then integrate with a payment gateway for payment purpose.
	- For the security part OAuth2.0  with InMemory is used as this project is just a demo but this should be replaced by integration with identity server like Keycloak for production purpose.
	   