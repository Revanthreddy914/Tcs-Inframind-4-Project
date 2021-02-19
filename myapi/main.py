import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
import pyrebase
import random
import schedule
import time
import smtplib
from datetime import datetime

from cryptography.fernet import Fernet
key = Fernet.generate_key()
f = Fernet(key)
now = datetime.now()

num2=0
conn = smtplib.SMTP('smtp.gmail.com',587)
conn.starttls()
conn.login("revanthreddy914@gmail.com","143@somu")


cred = credentials.Certificate("./revanth.json")
default_app=firebase_admin.initialize_app(cred)

firebaseConfig= {'apiKey': "AIzaSyALN-fwrDj7R8tJSR-mCEYlI21LBSlK8TE",
    'authDomain': "newproj-b6cee.firebaseapp.com",
    'databaseURL':"https://newproj-b6cee-default-rtdb.firebaseio.com/",
    'projectId': "newproj-b6cee",
    'storageBucket': "newproj-b6cee.appspot.com",
    'messagingSenderId': "1086026728086",
    'appId': "1:1086026728086:web:4cda972dc1aba8e7e3b8b7",
    'measurementId': "G-CGN1LXQQ0Y"
}
firebase = pyrebase.initialize_app(firebaseConfig)


db=firebase.database()
auth=firebase.auth()
storage=firebase.storage()
db2=firestore.client()

print("if you are already a user press 1 to login or 0 to sign up:")
num=int(input())
if num==0:
    # signup
    email1=input("enter email:")
    password1=input("enter password:")
    user=auth.create_user_with_email_and_password(email1,password1)
    auth.send_email_verification(user['idToken'])
    print("To login to your account press 2:")
    num2=int(input())
if num==1:

    #login
    email=input("enter email:")
    id=input("Enetr your EmployeeID")
    print("this id shoud be remembered")
    password=input("enter password")
    auth.sign_in_with_email_and_password(email, password)
    try:

        print("sucessfully signed in")
    except:
        print("invalid user name or password")
        exit()
if num2==2:
    # login
    email = input("enter email:")
    id = input("enter EmployeeId")
    password = input("enter password")
    auth.sign_in_with_email_and_password(email,password)


    try:

        print("sucessfully signed in")
    except:
        print("invalid user name or password")
        exit()

#storage


def rat():
    hbeat=random.randint(60,120)
    if (hbeat > 100):
        conn.sendmail('revanthreddy914@gmail.com', email, """your heartbeat is too high take some precautions
                                            1.Reduce stress
                                            2.Avoid tobacco products""")

    glucse=random.randint(20,250)
    if (glucse > 200):
        conn.sendmail('revanthreddy914@gmail.com', email, """your glucose level is too high take some rest
                                         1.after two hours indicates diabetes
                                         2.Take medications
                                         3.Drink Water as Your Primary Beverage""")

    if (glucse> 149 or glucse < 199):
        conn.sendmail('revanthreddy914@gmail.com', email, """your glucose level is too high take some rest
                                        1. indicates prediabetes
                                        2.Eat more fiber-rich foods
                                        3.Eat lean meats""")


    bp=random.randint(90,130)
    if (bp > 120):
        conn.sendmail('revanthreddy914@gmail.com', email, """your blood pressure is high take some precautions:
                                         1.Reduce your stress
                                         2.Reduce sodium in your diet""")


    oxygen=random.randint(80,100)
    if (oxygen > 80 or oxygen < 90):
        conn.sendmail('revanthreddy914@gmail.com', email, """your oxygenSaturation is low take some precautions:""")
    bodytemp = random.randint(97,105)
    if (bodytemp>100):
        conn.sendmail('revanthreddy914@gmail.com', email, """your bodytemperature is high take some precautions:
                                                            1.Drink cool liquids
                                                            2.Go somewhere with cooler air.
                                                            3.Move less.""")

    respi=random.randint(10,20)
    if (respi>20):
        conn.sendmail('revanthreddy914@gmail.com', email, """your respiration is high take some precautions:
                                           1.Lie down and place the hands on the abdomen.
                                           2.Press the lips together, keeping a small gap between them
                                           3.Inhale through the nose for a couple of seconds.""")
    electro=random.randint(120,200)
    if (electro>180):
        conn.sendmail('revanthreddy914@gmail.com', email, """your oxygenSaturation is high you might get:
                                                        1.high blood pressure (hypertension)
                                                        2.heart attack (myocardial infarction).""")
    now = datetime.now()
    current= now.strftime("%H:%M:%S")
    db.child(id).update({'heartbeat': hbeat, 'glucose': glucse, 'bp': bp,'oxygen':oxygen,'time':current,'bodytemp':bodytemp,'respiration':respi,'electro':electro})
    db2.collection(id).add({'heartbeat': hbeat, 'glucose': glucse, 'bp': bp,'oxygen':oxygen,'time':current,'bodytemp':bodytemp,'respiration':respi,'electro':electro})





schedule.every(30).seconds.do(rat)
while 1:
    schedule.run_pending()
    time.sleep(1)
