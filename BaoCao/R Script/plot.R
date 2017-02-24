d = c(139, 139, 123, 123, 67, 67, 35, 35, 54, 54, 84, 84, 134, 134, 167, 167, 98, 98, 79, 79, 176, 176, 97, 97, 162, 162, 230, 230, 244, 244, 159, 159, 185, 185, 285, 285, 172, 172, 182, 182, 143, 143, 234, 234, 231, 231, 125, 125, 186, 186, 166, 166, 182, 182, 197, 197, 203, 203, 195, 195, 195, 195, 197, 197, 146, 146, 71, 71, 160, 160, 194, 194, 199, 199, 199, 199, 136, 136, 131, 131, 174, 174, 191, 191, 138, 138, 164, 164, 213, 213, 118, 118, 0, 0, 0, 0, 156, 156, 198, 198, 276, 276, 298, 298, 0, 0, 0, 0, 62, 62, 200, 200, 224, 224, 144, 144, 342, 342, 120, 120, 79, 79, 269, 269, 383, 383, 357, 357, 263, 263, 116, 116, 245, 245, 329, 329, 363, 363, 357, 357, 374, 374, 331, 331, 97, 97, 151, 151, 257, 257, 160, 160, 166, 166, 234, 234, 75, 75, 93, 93, 283, 283, 380, 380, 313, 313, 0, 0, 0, 0, 54, 54, 93, 93, 264, 264, 130, 130, 216, 216, 0, 0, 73, 73, 217, 217, 169, 169, 53, 53, 32, 32, 60, 60, 53, 53, 38, 38, 49, 49, 55, 55, 46, 46, 78, 78, 67, 6)
x=length(d)
#plot(seq(0,23,by=1),d,type='b',col='red',main="Confucius Temple",xlab="Hours",ylab="Numbers of check-in")
#png(filename="test.png")
temp <- plot(1:x, d, type='b', axes=FALSE,xlab = "bước", ylab = "giá trị (m)")
temp <- axis(side=1, at=c(1:x))
temp <- axis(side=2, at=seq(min(d), max(d), by=100))
temp <- box()
#print(temp)
#dev.off()



plot(1:x, d, type='b', axes=FALSE)
axis(side=1, at=c(1:x))
axis(side=2, at=seq(min(d), max(d), by=100))
box()

dev.print(pdf, 'filename.pdf')
dev.off ();

# Create the function.
getmode <- function(v) {
  uniqv <- unique(v)
  uniqv[which.max(tabulate(match(v, uniqv)))]
}
result <- getmode(d)
result
estimate_mode <- function(x) {
  d <- density(x)
  d$x[which.max(d$y)]
}
result <- estimate_mode(d)
result
hist(d,breaks=seq(0,4000,by=100))
t.test(d)
e=c(113,1707,3008,21,175,164,1527,1281,13,0,2,77,173,379,347,149,173,278,276,192,253,366,177,260,50,60,62,376,420,135,114,283,365,52,120,80,383,2516,3560,2499,287,197,365,859,83,328,191,324,343,306,263,315,174,276,429,326,202,50,340,379,297,142,162,408,230,252,139,344,163,503,480,412,109,18,211,455,195,297,208,139,331,183,385,188,168,265,358,264,453,411,1060,387,219,8,154)
x=length(e)
#plot(seq(0,23,by=1),d,type='b',col='red',main="Confucius Temple",xlab="Hours",ylab="Numbers of check-in")
plot(1:x, e, type='b', axes=FALSE)
axis(side=1, at=c(1:x))
axis(side=2, at=seq(min(e), max(e), by=100))
box()
hist(e)
result <- getmode(e)
result
result <- estimate_mode(e)
result
as.data.frame(table(d))
as.data.frame(table(e))

cut(e, breaks=seq(from=241.5865,to=372.9817,by=5)) -> wait4
as.data.frame(table(wait4))

setwd("/home/thuy1/git/predictUsingProbability/Preprocess")
dataFile = "data_80.txt"
count.fields(dataFile, sep = ",")
no_col <- max(count.fields(dataFile, sep = ","))
no_col
dat=read.table(dataFile, header = FALSE, sep = ",", 
                col.names = 1:no_col, fill = TRUE)
i=1
for (i in 1:nrow(dat)){
  d=dat[i,]
  d=d[!is.na(d)]
  x=length(d)
  cat(paste("..", x, ".."))
  #plot(seq(0,23,by=1),d,type='b',col='red',main="Confucius Temple",xlab="Hours",ylab="Numbers of check-in")
  png(filename=paste("test_80_", i, ".png", sep = ""))
  temp <- plot(1:x, d, type='b', axes=FALSE,xlab = "step", ylab = "length (meters)")
  temp <- axis(side=1, at=c(1:x))
  temp <- axis(side=2, at=seq(min(d), max(d), by=100))
  temp <- box()
  print(temp)
  dev.off()
}


conn <- file("/home/thuy/workspace/Preprocess/data.txt",open="r")
linn <-readLines(conn)
as.numeric(linn[1])
d=c(as.numeric(linn[1]))
d
for (i in 1:length(linn)){

  print(linn[i])
}
close(conn)

N1 <- c("1000, 1000, 10000, 10,000")
as.numeric(N1)
