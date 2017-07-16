library(frbs)
getwd()
setwd('/home/thuy1/git/predictUsingProbability/Preprocess')
data=read.table(file="classifyRoute.csv", header=FALSE, sep=",",
                col.names = c("X1","X2","X3","X4","X5","Clazz"));
data
nrow(data)
sample(nrow(data))
dataShuffled <- data[sample(nrow(data)),]
dataShuffled
dataShuffled[,6] <- unclass(dataShuffled[,6])
dataShuffled
learnDataCount <- 996
tra.data <- dataShuffled[1:learnDataCount,]
tra.data
#tst.data <- dataShuffled[learnDataCount:nrow(dataShuffled),1:5]
#tst.data
#real.data <- matrix(dataShuffled[learnDataCount:nrow(dataShuffled),6], ncol = 1)
#real.data
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
tst.data=read.table(file="testData_AS-CC.csv", header=FALSE, sep=",",
                col.names = c("X1","X2","X3","X4","X5"));
res.test <- predict(object.cls, tst.data)
res.test
outputData = cbind(tst.data, res.test)
classify <- function(x, output) {
  class=ifelse(x[6] == 2,"onTime","lateTime")
  cat(paste(x[1],x[2], x[3], x[4], x[5], class, sep=","), file= output, append = T, fill = T)
}
apply(outputData, 1, classify, output = 'classify4OtherMethod.csv')
