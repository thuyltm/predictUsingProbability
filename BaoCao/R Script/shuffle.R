library(frbs)
getwd()
setwd('/home/thuy1/git/predictUsingProbability/Preprocess')
data=read.table(file="classifyRouteSuffle.csv", header=FALSE, sep=",",
                col.names = c("X1","X2","X3","X4","X5","Clazz"));
data
nrow(data)
sample(nrow(data))
dataShuffled <- data[sample(nrow(data)),]
dataShuffled
dataShuffled[,6] <- unclass(dataShuffled[,6])
learnDataCount <- 170
tra.data <- dataShuffled[1:learnDataCount,]
tra.data
tst.data <- dataShuffled[learnDataCount:nrow(dataShuffled),1:5]
tst.data
real.data <- matrix(dataShuffled[learnDataCount:nrow(dataShuffled),6], ncol = 1)
real.data
#convert matrix to vector
as.vector(real.data)
## Please take into account that the interval needed is the range of input data only.
range.data.input <- matrix(apply(data[, -ncol(data)], 2, range), nrow = 2)
range.data.input
## Set the method and its parameters. In this case we use FRBCS.W algorithm
method.type <- "FRBCS.W"
control <- list(num.labels = 7, type.mf = "GAUSSIAN", type.tnorm = "MIN",
                type.snorm = "MAX", type.implication.func = "ZADEH")

## Learning step: Generate fuzzy model
object.cls <- frbs.learn(tra.data, range.data.input, method.type, control)

## Predicting step: Predict newdata
res.test <- predict(object.cls, tst.data)
res.test
c(res.test)
real.data
## Display the FRBS model
summary(object.cls)

## Plot the membership functions
plotMF(object.cls)

