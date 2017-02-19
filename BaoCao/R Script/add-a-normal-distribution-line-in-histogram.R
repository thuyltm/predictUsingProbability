setwd("/home/thuy1/git/predictUsingProbability/Preprocess/")
mydata = read.csv("freqHigh_LateTime.csv",header=FALSE)[ ,1]
mydata
hist(mydata)
d<-density(mydata)
plot(d)
polygon(d, col="red", border="blue")
grades <- mydata
hist(grades, prob=TRUE)
curve(dnorm(x, mean=mean(grades), sd=sd(grades)), add=TRUE)
