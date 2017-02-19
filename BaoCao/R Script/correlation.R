getwd()
setwd('/home/thuy/workspace/Translator')
data = read.csv("accumulate.csv",header = TRUE, sep=",",
                      col.names = c("X1","X2","X3","X4","X5","Total"))
data
####################################################
############### Pair Comparasion
####################################################
png(filename="pairCompare.png")
pairs(data)
dev.off()
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
#curve(dnorm(x,mean=mean(data$X1),sd=sd(data$X1)),add=TRUE)
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
#curve(dnorm(x,mean=mean(data$X2),sd=sd(data$X2)),add=TRUE)
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
#curve(dnorm(x,mean=mean(data$X3),sd=sd(data$X3)),add=TRUE)
dev.off()
t.test(X3,mu=mean(data$X3))
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
#curve(dnorm(x,mean=mean(data$X4),sd=sd(data$X4)),add=TRUE)
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
#curve(dnorm(x,mean=mean(data$X5),sd=sd(data$X5)),add=TRUE)
dev.off()
t.test(X5,mu=mean(data$X5))
####################################################
###############Consider Total
####################################################
layout(1)
Total=data$Total
summary(Total)
png(filename="Total.png")
plot(Total)
hist(Total,probability = TRUE)
lines(density(Total))
lines(density(Total,adjust=2),lty="dotted")
#curve(dnorm(x,mean=mean(Total),sd=sd(Total)),add=TRUE)
dev.off()
t.test(Total,mu=mean(data$Total))
res.lm=lm(Total~.,data=data)
res.lm
####################################################
###############Pair Compare
####################################################
data=read.table(file="pairCompare.csv", header=FALSE, sep=",",
                col.names = c("time","Variable"))
data$Variable=as.factor(data$Variable)
boxplot(time~Variable,data=data)
oneway.test(time~Variable,data=data,var.equal = T)
####################################################
###############Compare
####################################################
library("ggplot2")
data=read.table(file="compare.csv", header=TRUE, sep=",",
                col.names = c("path","time","percent","var"))
qplot(time, path, data = data, colour = var)
layout(matrix(1:5, ncol = 5))
####################################################
###############data$var==V1
####################################################
d1=subset(data,data$var=='V1')
plot(time~path,data=d1)
lm(time~path,data=d1)
abline(lm(time~path,data=d1))
####################################################
###############data$var==V2
####################################################
d2=subset(data,data$var=='V2')
plot(time~path,data=d2)
lm(time~path,data=d2)
abline(lm(time~path,data=d2))
####################################################
###############data$var==V3
####################################################
d3=subset(data,data$var=='V3')
plot(time~path,data=d3)
lm(time~path,data=d3)
abline(lm(time~path,data=d3))
####################################################
###############data$var==V4
####################################################
d4=subset(data,data$var=='V4')
plot(time~path,data=d4)
lm(time~path,data=d4)
abline(lm(time~path,data=d4))
####################################################
###############data$var==V5
####################################################
d5=subset(data,data$var=='V5')
plot(time~path,data=d5)
lm(time~path,data=d5)
abline(lm(time~path,data=d5))
qplot(time, path, data = d5, geom = "line",xlim=c(50,700),binwidth = 50)
###################################################
qplot(var,path/time,data=data,geom="boxplot")
layout(matrix(1:5, ncol = 5))
qplot(path/time, data = d1, geom = "histogram")
qplot(path/time, data = d1, geom = "density")
qplot(path/time, data = d2, geom = "histogram")
qplot(path/time, data = d2, geom = "density")
qplot(path/time, data = d3, geom = "histogram")
qplot(path/time, data = d3, geom = "density")
qplot(path/time, data = d4, geom = "histogram")
qplot(path/time, data = d4, geom = "density")
qplot(path/time, data = d5, geom = "histogram")
qplot(path/time, data = d5, geom = "density")
qplot(path/time, data = data, geom = "density", colour = var)
qplot(percent, data = data, geom = "histogram", fill = var)
qplot(percent, data = data, facets = var ~ ., geom = "histogram")
qplot(path/time, data = data, facets = var ~ ., geom = "density")
qplot(path/time, data = data, facets = var ~ ., geom = "density",xlim = c(0, 30))
qplot(path/time, data = data, facets = var ~ ., geom = "density",xlim = c(30, 60))
qplot(path/time, data = data, facets = var ~ ., geom = "density",xlim = c(60, 120))
###############################################################
