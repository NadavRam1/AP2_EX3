# AP2_EX3

# Some important notes before we begin

Unfurtunately, we didn't managed to finish our entire program as we wanted in time. 

It doesn't mean that we don't have a working application, it's just not perfect the way we wanted it to be.

We have 4 issues / notes:

- Not a big issue but still a problem, the profile picture you will pic during registration won't be shown to you.
- In order for user X to send a message to user Y, both users must login at least once since their registration.
  It's obvious that in order to send message user X must be loged in, but in order to receive messages user Y must also login to the app after registration.

- We wasn't able to finish the functionality of changing the server address from the app on time, and therefore decieded to remove it.
  At the beggining it worked but then we has some bugs we didn't managed to fix.
  The problem is that we think we may left some part of the implementation in code, and therefore when trying to change the server address from the app
  it would probably crash because of the use in shared preferences and problems we didn't fixed, so don't try it.
  

- For now, the default server address is with the IP address of one of our computers, therefore you should change the address from the string.xml
  file before trying tp run the app.
  
We know this isn't how it should have been done, but regarding those issued our app is well functional
  



#installations required before running the program

In order for the project to run as it is supposed to, you should install some packages before trying to run the program.

You should download the packages using the next command:

npm i react react-dom react-router-dom react-scripts express socket.io socket.io-client mongodb jsonwebtoken

# running the program

In order to run the program, you can choose between two options:

- If you have your own server and you would like to use only our design, you should change the base_url strings in the strings.xml file to the addrss of your server.

- If you would like to use also our server, you will still need to change the the address to include the IP of your personal computer.

Then all you need to do is to run our program using your personal phone or an emulator.

# signing up

The first page that will be showed to you while running the program is the register page.

In this page, you will need to sign up in order to be able to access the chats page.

The registering process will require you to fill this fields:

- Username (must contain only letters with no numbers and/or special characters)
  This is the name you will use to login to the app after signing up. This username is unique and only you have it.

- Password (must contain at least 8 characters, and include at least 1 uppercase letter, 1 lowercase letter, and 1 digit. 
  May also contain the special characters '!' and '*').
  We highly recommend choosing a strong password, and a password that isn't already being used elsewhere.
  
  After entering the password you chose, you will need to type it again for confirmation.
  This is the password you will use to login to the app after signing up

- Display name (must contain only letters with no numbers and/or special characters)
  This is the name that will be displayed for your friends and contacts that are also using the app.

- picture
  You should choose some photo from the local storage of your computer.
  This is the photo that will be presented to your friends and contacts.

  After filling all those fields, you will need to confirm that you are not a robot.
  
  Then, if all the fields have appropriate values (indication will be provided when the values aren't matching for some reason),
  you will be transferred to the login page.
  
  # login
  Once you registered correctly to the app, you will be able to login using your username and password.
  Note that you will be able to return to the register page in order to create many more different accounts.
  
  If the details you provide when trying to login will match the details you signed up with, you will be transferred to the chats page,     where the true fun begins.
  
  It's gonna be legen (wait for it)                                                                        dary!
  
  # Chatting
  Now that you signed in correctly, you may start enjoying our app.
  For starters, you can see that the picture and name you chose are displayed on the screen (at least they should have)

  
  Now, We will provide a short tutorial about what is in our program:

  - Navigation syste between the different pages of our program.
  - Option for adding new contacts:
  You will be able to add contacts to your list (note that you will not be able to add unregistered contacts, and/or add the same contact twice and have with him multiple chats).
   In order to add a new contact, just press the 'add contacts' icon on the right botton of the screen, and then type the name of the contact you      would like to add. And again, note that if this person is not reistered or already been added to your chats list you won't be able to add him to your chats.
   
   It also should be mentioned that this is not a psychedelic experience and therefore you won't be able to chat with yourself, if you would like to do that I recommend you go to a place where it is legal and then ask the local shaman for the good stuff
  
  Once adding a new contact to your list, you will be able to send him and receive from him as many messages as you want. Isn't that great?
  
  But wait, theres more!
  
  - Support in switching between contacts:
  Imagine that you could not just add many contacts, but you could send all of them as many messages as you like.
  Sound amazing isn't it? Well...
  
  IT IS POSSIBLE!! You will be able to send each one of your contacts all the messages you like.
  
  You think that's it? It's just getting better!
  
  - Logout option:
  If for some reason, you will feel that you had enough from our app, I would suggest that you will consider going to therapy because       clearly youre not well. But, if you do want to stop using the app, you could navigate to the logout page.
  Then, you will need to confirm that that you are absolutely sure you want to logout.
  If that is the case, your (although stupid) wish is our command, and you will be returned to the login page.
  If you choose to stay in our app (like a true man!), your will be returned to the home page.
  
  The messages that you sent / received and all your chats will be saved for you when you will return to use our app, and you will.
  
  Eventually, if you would like to login again you can, and basically you could do anything that you want.
  Except maybe from killing people, or stealing, or driving drunk, or late submitting some of Hemi's targilonim (you should be ashamed if   you did, SHAME ON YOU!). Do what you like as long as its legal and doesn't violate Geneva Conventions.
  
  We hope you will enjoy using our app, and we are (not) open for any improvements that you might have. Adiós.
  






