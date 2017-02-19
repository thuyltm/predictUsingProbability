################################################
##### The Akaike information criterion##########
################################################
library(UsingR)
pairs(stud.recs)
d=subset(stud.recs,select=-letter.grade)
res.lm=lm(num.grade~.,data=d)
res.lm
################################################
#####qplot##########
################################################
library("ggplot2")
data("diamonds")
diamonds
set.seed(1410)
dsmall=diamonds[sample(nrow(diamonds),100),]
dsmall
qplot(carat,price,data=diamonds)
qplot(log(carat),log(price),data=diamonds)
qplot(carat,x*y*z,data=diamonds)
qplot(carat, price, data = dsmall, colour = color)
qplot(carat, price, data = dsmall, shape = cut)


X <- rep(c(5, 2, 5), each=10)
X <- X + c(rnorm(10, sd=0.5), rnorm(10, sd=0.2), rnorm(10, sd=0.1)) 
plot(X, 
     ylim=c(0,8),
     ylab="Weight")
lines(c(1,10), c(5,5))
lines(c(11,20), c(2,2))
lines(c(21,30), c(5,5))
text(5.5, 7, "A")
text(15.5, 4, "B")
text(25.5, 7, "C")
