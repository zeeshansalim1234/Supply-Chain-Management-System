import nltk,numpy
from nltk.stem.snowball import SnowballStemmer
from nltk.stem import WordNetLemmatizer
"""
lemmatizer = WordNetLemmatizer()

nltk.download()

stemmer = SnowballStemmer("english")

sentence = "My name is Zeehsan Salim Chougle ....."

print(stemmer.stem("generously"))     #Gives the root word

tokens = nltk.word_tokenize(sentence)   #Divides sentence into tokens in a list
print(tokens)

tags = nltk.pos_tag(tokens)
print(tags)

entities = nltk.chunk.ne_chunk(tags)    #Name entity recognition
print(entities)"""


import csv,numpy,re,emoji,spacy
from nltk.tokenize import word_tokenize
from nltk.corpus import stopwords

sp=spacy.load()
tweets=[]   # contains all tweets
sizes=[]    # contains sizes of all tweets
num_tweets = 0 # Number of tweets

def extract_tweets():

    with open("C:\\Users\\hp\\Desktop\\inventory\\tweets.csv","r",encoding='UTF-8') as file:
        reader = csv.DictReader(file)

        for row in reader:

            if(row is not None):
                tweets.append(row['text'])


def calculate_average():

    for i in range(0, len(tweets)):

        if (tweets[i]):
            sizes.append(len(tweets[i]))

    sum = 0

    for i in range(0, len(sizes)):
        sum += sizes[i]

    return (int(sum / len(sizes)))

def pre_process_tweets() :

    for i in range (0,len(tweets)) :

        if(tweets[i] is not None)  :


            tweets[i]=tweets[i].replace('@','')
            tweets[i]=tweets[i].replace('#','')
            tweets[i]=remove_urls(tweets[i])
            tweets[i]=remove_emojis(tweets[i])

    return tweets


def remove_urls (str):

    str = re.sub(r'(https|http)?:\/\/(\w|\.|\/|\?|\=|\&|\%)*\b', '', str, flags=re.MULTILINE)
    return(str)


def remove_emojis(data):

    emoji = re.compile("["
        u"\U0001F600-\U0001F64F"  # emoticons
        u"\U0001F300-\U0001F5FF"  # symbols & pictographs
        u"\U0001F680-\U0001F6FF"  # transport & map symbols
        u"\U0001F1E0-\U0001F1FF"  # flags (iOS)
        u"\U00002500-\U00002BEF"  # chinese char
        u"\U00002702-\U000027B0"
        u"\U00002702-\U000027B0"
        u"\U000024C2-\U0001F251"
        u"\U0001f926-\U0001f937"
        u"\U00010000-\U0010ffff"
        u"\u2640-\u2642" 
        u"\u2600-\u2B55"
        u"\u200d"
        u"\u23cf"
        u"\u23e9"
        u"\u231a"
        u"\ufe0f"  # dingbats
        u"\u3030"
                      "]+", re.UNICODE)
    return re.sub(emoji, '', data)


def get_tokens() :

    temp_str=""

    for i in range(0, len(tweets)):
        if(tweets[i] is not None) :
            temp_str+=tweets[i]

    tokens = word_tokenize(temp_str)

    return tokens

def remove_stopwords() :

    all_stopwords = sp.Defaults.stop_words
    tokens_without_sw = [word for word in tokens if not word in all_stopwords]
    return tokens_without_sw

def display_tweets() :

    for i in range (0,len(tweets)) :

        print(tweets[i])
        print("\n -------------------------------------------------------------------------------------------------\n")


extract_tweets()
average=calculate_average()
pre_process_tweets()
display_tweets()
num_tweets=len(tweets)
tokens=get_tokens()
tokens_without_sw = remove_stopwords()



print("Number of tweets : " + str(num_tweets))
print("Average size of tweets : "+ str(average))
print("Number of tokens : "+str(len(tokens)))
print("Number of tokens without stopwords : "+str(len(tokens_without_sw)))



