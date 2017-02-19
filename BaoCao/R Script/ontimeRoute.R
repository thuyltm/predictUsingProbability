getwd()
setwd('/home/thuy/workspace/Translator')
data=read.table(file="accumulate.csv", header=FALSE, sep=",",
                col.names = c("id","X1","X2","X3","X4","X5","Total"));
summary(data)
####################################################
###############Consider X1
####################################################
layout(matrix(1:2, ncol = 2))
X1=data$X1
summary(X1)
png(filename="X1.png")
plot(X1)
hist(X1,probability = TRUE)
lines(density(X1))
lines(density(X1,adjust=2),lty="dotted")
curve(dnorm(x,mean=mean(data$X1),sd=sd(data$X1)),add=TRUE)
dev.off()
t.test(X1,mu=mean(data$X1))
####################################################
###############Consider X2
####################################################
layout(matrix(1:2, ncol = 2))
X2=data$X2
summary(X2)
png(filename="X2.png")
plot(X2)
hist(X2,probability = TRUE)
lines(density(X2))
lines(density(X2,adjust=2),lty="dotted")
curve(dnorm(x,mean=mean(data$X2),sd=sd(data$X2)),add=TRUE)
dev.off()
t.test(X2,mu=mean(data$X2))
####################################################
###############Consider X3
####################################################
layout(matrix(1:2, ncol = 2))
X3=data$X3
summary(X3)
png(filename="X3.png")
plot(X3)
hist(X3,probability = TRUE)
lines(density(X3))
lines(density(X3,adjust=2),lty="dotted")
curve(dnorm(x,mean=mean(data$X3),sd=sd(data$X3)),add=TRUE)
dev.off()
t.test(X1,mu=mean(data$X3))
####################################################
###############Consider X4
####################################################
layout(matrix(1:2, ncol = 2))
X4=data$X4
summary(X4)
png(filename="X4.png")
plot(X4)
hist(X4,probability = TRUE)
lines(density(X4))
lines(density(X4,adjust=2),lty="dotted")
curve(dnorm(x,mean=mean(data$X4),sd=sd(data$X4)),add=TRUE)
dev.off()
t.test(X4,mu=mean(data$X4))
####################################################
###############Consider X5
####################################################
layout(matrix(1:2, ncol = 2))
X5=data$X5
summary(X5)
png(filename="X5.png")
plot(X5)
hist(X5,probability = TRUE)
lines(density(X5))
lines(density(X5,adjust=2),lty="dotted")
curve(dnorm(x,mean=mean(data$X5),sd=sd(data$X5)),add=TRUE)
dev.off()
t.test(X5,mu=mean(data$X5))
####################################################
###############Consider Total
####################################################
layout(1)
Total=data$Total
summary(Total)
png(filename="X5.png")
plot(Total)
hist(Total,probability = TRUE)
lines(density(Total))
lines(density(Total,adjust=2),lty="dotted")
curve(dnorm(x,mean=mean(Total),sd=sd(Total)),add=TRUE)
dev.off()
layout(matrix(1:3, nrow=1, ncol = 3))
plot(Total ~ X1, data = data)
plot(Total ~ X2, data = data)
plot(Total ~ X3, data = data)
plot(Total ~ X4, data = data)
plot(Total ~ X5, data = data)
relation <-lm(data$Total~X1+X2+X3+X4+X5, data=data)


