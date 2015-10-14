CREATE TABLE [cukes].[Runs]
(
	[Id] BIGINT NOT NULL  PRIMARY KEY IDENTITY(1,1), 
    [Environment] VARCHAR(50) NULL, 
    [Suite] VARCHAR(50) NULL, 
    [Start] DATETIME NULL, 
    [End] DATETIME NULL
)


GO

CREATE INDEX [IX_Runs_ID] ON [cukes].[Runs] ([Id])
