CREATE TABLE [cukes].[Scenarios]
(
	[Id] BIGINT NOT NULL PRIMARY KEY IDENTITY(1,1),
	[ScenarioID] VARCHAR(100) NULL,
    [Name] VARCHAR(100) NULL, 
    [Description]  VARCHAR(500) NULL,
	[Type] VARCHAR(20) NULL, 
	[Version] [bigint] NOT NULL,
    [ValidFrom] DATETIME NULL, 
    [ValidTo] DATETIME NULL
)

GO

CREATE INDEX [IX_Scenarios_ID] ON [cukes].[Scenarios] ([Id])
