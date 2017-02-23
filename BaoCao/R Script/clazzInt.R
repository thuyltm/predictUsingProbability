library(classInt)
getwd()
setwd('/home/thuy1/git/predictUsingProbability/Preprocess')
data=read.table(file="freqVerySmall.csv")[,1]
data
classIntervals(data, n = 5, style = "jenks")
data=read.table(file="freqSmall.csv")[,1]
data
classIntervals(data, n = 5, style = "jenks")
data=read.table(file="freqMedium.csv")[,1]
data
classIntervals(data, n = 5, style = "jenks")
data=read.table(file="freqHigh.csv")[,1]
data
classIntervals(data, n = 5, style = "jenks")
data=read.table(file="freqVeryHigh.csv")[,1]
data
classIntervals(data, n = 5, style = "jenks")