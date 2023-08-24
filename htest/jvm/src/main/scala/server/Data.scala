package server

object Data:
    def randoms(min: Int, max: Int, count: Int) = 
        Range(0, count).map(_ => math.random() * (max - min) + min)

    trait HasDouble[A]:
        def doubleValue(a: A): Double

    object HasDouble:
        given HasDouble[Double] with 
            def doubleValue(d: Double): Double = d

object BoxData:
    import scala.collection.SeqOps
    import Data.HasDouble

    // https://www.scribbr.com/statistics/quartiles-quantiles/
    def apply[A, B[A] <: SeqOps[A, _, _]](as: B[A], isSorted: Boolean = false)(using af:HasDouble[A]) = 
        val sorted: B[A] = if isSorted then as else as.sortBy(a => af.doubleValue(a)).asInstanceOf[B[A]]
        val len = sorted.length
        val maxIndex = len-1
        val qs = Range(1, 4).map(i =>
            val dindex = len * i * 0.25 
            if (dindex - dindex.floor) == 0 then 
                val iindex = dindex.floor.toInt min maxIndex
                val nindex = (iindex + 1) min maxIndex
                (af.doubleValue(sorted(iindex)) + af.doubleValue(sorted(nindex))) / 2.0
            else
                af.doubleValue(sorted(dindex.ceil.toInt min maxIndex))
        )

        val outbound = (qs(2) - qs(0)) * 1.5
        val lowerbound = qs(0) - outbound
        val upperbound = qs(2) + outbound

        val (lowOutliers, rest, highOutliers) = sorted.foldLeft((Seq.empty[A], Seq.empty[A], Seq.empty[A])) {
            case ((los, ds, hos), a) => 
                val dv = af.doubleValue(a)
                if dv < lowerbound then 
                    (los :+ a, ds, hos)
                else if dv > upperbound then
                    (los, ds, hos :+ a)
                else 
                    (los, ds :+ a, hos)
        }

        // https://medium.com/@agarwal.vishal819/outlier-detection-with-boxplots-1b6757fafa21
        val vmin = if af.doubleValue(sorted(0)) < lowerbound then lowerbound else af.doubleValue(sorted(0))
        val vmax = if af.doubleValue(sorted(maxIndex)) > upperbound then upperbound else af.doubleValue(sorted(maxIndex))
        (Seq(vmin, qs(0), qs(1), qs(2), vmax), lowOutliers, highOutliers, rest)