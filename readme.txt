Q) What is the Complexity of Algorithm?
A) I am reading the file line by line and then grouping by worker and processing result for each worker.
    Overall complexity is O(n)


Q) We provided a CSV file for convenience, but in a production environment,
   what format or database would you recommend instead for this use-case (and why)?
A) In production I would like to use document DB. In this scenario I would recommend Mongo DB, because this
    can query based on different categories of the fields used.
    As with many other database systems, MongoDB allows you to perform a variety of aggregation operations.
    These allow you to process data records in a variety of ways, such as grouping data, sorting data into a
    specific order, or restructuring returned documents, as well as filtering data as one might with a query.


Q) Is there anything else you would implement differently for a large-scale application in production?
A) For large-scale application, we can use distributed system for both application servers and database servers.
    We should use horizontal scaling appropriately so that multiple machines can work in parallel using map reduce
    strategy. We can map jobs to different machines and reduce those outputs to combine the result.

    We also can use different ingestion system in a hadoop cluster along with Kafka for batch or streaming jobs for post
    processing and analytics kind of activities.
    We can further integrate with spark cluster or storm to find trending topics or other statistics based on different
    fields of csv.

