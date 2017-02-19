line="===========================================================\n"
getwd()
setwd('/home/thuy/workspace/Translator')
fileName="myfile.txt"
sink(fileName, append=TRUE, split=TRUE)
routeAS_CC = read.csv("routeAS-CC.csv")
hist(routeAS_CC$seconds,prob=TRUE, col="grey")
lines(density(routeAS_CC$seconds), col="blue", lwd=2)
lines(density(routeAS_CC$seconds, adjust=2), lty="dotted", col="darkgreen", lwd=2)
out <- capture.output(summary(routeAS_CC$seconds))
cat("Summary of routeAS_CC$seconds\n", out, file=fileName, sep="\n", append=TRUE)
t.test(routeAS_CC$seconds,mu=2586,alternative = c("two.sided"))
cat(line,file=fileName,append=TRUE)
routeCC_AS = read.csv("routeCC-AS.csv");
jpeg("hist_routeCC_AS.jpg")
plot(hist(routeCC_AS$seconds))
curve(dnorm(x,mean=mean(routeCC_AS$seconds),sd=sd(routeCC_AS$seconds)),add=TRUE)
out <- capture.output(summary(routeCC_AS$seconds))
cat("Summary of routeAS_CC$seconds\n", out, file=fileName, sep="\n", append=TRUE)

t.test(routeCC_AS$seconds,mu=2545)

#out <- capture.output(summary(routeAS_CC$seconds))
#cat(out, file="summary_of_my_very_time_consuming_regression.txt", sep="\n", append=TRUE)


#jpeg("myplot.jpg")
#plot(hist(routeCC_AS$seconds))
#dev.off()